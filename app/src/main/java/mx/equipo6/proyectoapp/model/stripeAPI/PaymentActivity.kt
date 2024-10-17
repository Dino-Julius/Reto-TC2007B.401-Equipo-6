package mx.equipo6.proyectoapp.model.stripeAPI

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.view.CardInputWidget
import mx.equipo6.proyectoapp.R
import mx.equipo6.proyectoapp.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class PaymentIntentRequest(val amount: Int, val payment_method: String)
data class PaymentIntentResponse(val clientSecret: String)

class PaymentActivity : AppCompatActivity() {

    private lateinit var stripe: Stripe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        // Initialize Stripe SDK
        PaymentConfiguration.init(applicationContext, "pk_test_51Q5tAn1L09JDsSP2jjvRpSuZoeAOHruC8iXNxPZs6l8uQ7rWJ3mBqtOiPVDSuFEkWU5fs2lVsWnYa2FwSozvqg6r00ugcXQ5Ll")
        stripe = Stripe(applicationContext, PaymentConfiguration.getInstance(applicationContext).publishableKey)

        val cardInputWidget = findViewById<CardInputWidget>(R.id.cardInputWidget)
        val payButton = findViewById<Button>(R.id.payButton)

        payButton.setOnClickListener {
            // Get payment details from the card input widget
            val params = cardInputWidget.paymentMethodCreateParams

            if (params != null) {
                // Log the card params for debugging purposes
                Log.d("CardInputParams", params.toString())

                // Use coroutine to create PaymentMethod asynchronously
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val paymentMethod = stripe.createPaymentMethodSynchronous(params)
                        withContext(Dispatchers.Main) {
                            if (paymentMethod != null) {
                                paymentMethod.id?.let { paymentMethodId ->
                                    // Send PaymentMethod to server
                                    sendPaymentIntentToServer(paymentMethodId)
                                } ?: showToast("Failed to create PaymentMethod")
                            } else {
                                showToast("Failed to create PaymentMethod")
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            showToast("Error creating PaymentMethod: ${e.localizedMessage}")
                        }
                        Log.e("PaymentActivity", "Error creating PaymentMethod, ${e.localizedMessage}")
                    }
                }
            } else {
                showToast("Enter card details")
            }
        }
    }

    private fun sendPaymentIntentToServer(paymentMethodId: String) {
        val totalPrice = intent.getIntExtra("totalPrice", 0)

        // Log the amount being sent to the server
        Log.d("Amount", "Total Price (in cents): $totalPrice")

        val request = PaymentIntentRequest(amount = totalPrice, payment_method = paymentMethodId)

        RetrofitClient.apiService.createPaymentIntent(request).enqueue(object : Callback<PaymentIntentResponse> {
            override fun onResponse(call: Call<PaymentIntentResponse>, response: Response<PaymentIntentResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { paymentIntentResponse ->
                        val clientSecret = paymentIntentResponse.clientSecret

                        runOnUiThread {
                            // Confirm the payment
                            stripe.confirmPayment(
                                this@PaymentActivity,
                                ConfirmPaymentIntentParams.create(clientSecret)
                            )

                            // Finish the PaymentActivity after confirming the payment
                            showToast("Pago exitoso!")
                            Log.d("valores", "Pago Exitoso")

                            // Return the result of successful payment
                            setResult(Activity.RESULT_OK)
                            finish() // Close the PaymentActivity
                        }
                    }
                } else {
                    runOnUiThread {
                        showToast("Server error: ${response.message()}")
                        setResult(Activity.RESULT_CANCELED)
                        finish()
                    }
                }
            }

            override fun onFailure(call: Call<PaymentIntentResponse>, t: Throwable) {
                runOnUiThread {
                    showToast("Failed to create payment intent")
                    setResult(Activity.RESULT_CANCELED)
                    finish()
                }
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this@PaymentActivity, message, Toast.LENGTH_SHORT).show()
    }
}