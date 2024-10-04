package mx.equipo6.proyectoapp.stripeAPI

import android.app.Activity
import android.content.Intent
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
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONObject
import java.io.IOException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PaymentActivity : AppCompatActivity() {

    private lateinit var stripe: Stripe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        // Initialize Stripe SDK
        PaymentConfiguration.init(applicationContext, "pk_test_51Q5tAn1L09JDsSP2jjvRpSuZoeAOHruC8iXNxPZs6l8uQ7rWJ3mBqtOiPVDSuFEkWU5fs2lVsWnYa2FwSozvqg6r00ugcXQ5Ll") // Replace with your actual publishable key
        stripe = Stripe(applicationContext, PaymentConfiguration.getInstance(applicationContext).publishableKey)

        val cardInputWidget = findViewById<CardInputWidget>(R.id.cardInputWidget)
        val payButton = findViewById<Button>(R.id.payButton)

        payButton.setOnClickListener {
            // Get payment details from the card input widget
            val params = cardInputWidget.paymentMethodCreateParams

            if (params != null) {
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
                    }
                }
            } else {
                showToast("Enter card details")
            }
        }
    }

    private fun sendPaymentIntentToServer(paymentMethodId: String) {
        val totalPrice = intent.getIntExtra("totalPrice", 0)

        // JSON to send to the server
        val jsonBody = JSONObject().apply {
            put("amount", totalPrice)
            put("payment_method", paymentMethodId)
        }

        val body = RequestBody.create("application/json; charset=utf-8".toMediaType(), jsonBody.toString())
        val request = Request.Builder()
            .url("http://10.48.78.90:3000/create-payment-intent") // Replace with your server URL
            .post(body)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    showToast("Failed to create payment intent")
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    response.body?.let { responseBody ->
                        val responseString = responseBody.string()
                        val jsonResponse = JSONObject(responseString)
                        val clientSecret = jsonResponse.getString("clientSecret")

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
                        showToast("Server error: ${response.message}")
                        setResult(Activity.RESULT_CANCELED)
                        finish()
                    }
                }
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this@PaymentActivity, message, Toast.LENGTH_SHORT).show()
    }
}
