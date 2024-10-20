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

/**
 * Data class que contiene los datos necesarios para realizar el pago
 * @param amount: cantidad a pagar
 * @param payment_method: método de pago
 * @author Jesús Guzmán | A01799257
 */
data class PaymentIntentRequest(val amount: Int, val payment_method: String)

/**
 * Data class que contiene el client secret del pago
 * @param clientSecret: client secret del pago
 * @author Jesús Guzmán | A01799257
 */
data class PaymentIntentResponse(val clientSecret: String)

/**
 * Activity que se encarga de realizar el pago de la compra, integración del servicio
 * de Stripe en la aplicación
 * @property stripe: objeto de la clase Stripe
 * @constructor Crea un objeto de la clase PaymentActivity
 * @author Jesús Guzmán | A01799257
 */
class PaymentActivity : AppCompatActivity() {

    // Inicialización de la clase Stripe
    private lateinit var stripe: Stripe

    /**
     * Función que se ejecuta al crear la actividad
     * @param savedInstanceState: instancia de la actividad
     * @author Jesús Guzmán | A01799257
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        // Initialize Stripe SDK
        PaymentConfiguration.init(applicationContext, "pk_test_51Q5tAn1L09JDsSP2jjvRpSuZoeAOHruC8iXNxPZs6l8uQ7rWJ3mBqtOiPVDSuFEkWU5fs2lVsWnYa2FwSozvqg6r00ugcXQ5Ll")
        stripe = Stripe(applicationContext, PaymentConfiguration.getInstance(applicationContext).publishableKey)

        // Obtiene el CardInputWidget y el widget de botón de pago
        val cardInputWidget = findViewById<CardInputWidget>(R.id.cardInputWidget)
        // Obtiene el botón de pago
        val payButton = findViewById<Button>(R.id.payButton)

        // Listener del botón de pago
        payButton.setOnClickListener {
            // Obitne los parámetros del método de pago
            val params = cardInputWidget.paymentMethodCreateParams

            if (params != null) {
                // Log the card params for debugging purposes
                Log.d("CardInputParams", params.toString())

                // Usa un coroutine para crear PaymentMethod de forma asíncrona
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

    /**
     * Función que envía el intento de pago al servidor
     * @param paymentMethodId: id del método de pago
     * @author Jesús Guzmán | A01799257
     */
    private fun sendPaymentIntentToServer(paymentMethodId: String) {
        val totalPrice = intent.getIntExtra("totalPrice", 0)
        Log.d("Amount", "Total Price (in cents): $totalPrice")

        // Crea el objeto PaymentIntentRequest
        val request = PaymentIntentRequest(amount = totalPrice, payment_method = paymentMethodId)

        RetrofitClient.apiService.createPaymentIntent(request).enqueue(object : Callback<PaymentIntentResponse> {
            override fun onResponse(call: Call<PaymentIntentResponse>, response: Response<PaymentIntentResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { paymentIntentResponse ->
                        val clientSecret = paymentIntentResponse.clientSecret

                        runOnUiThread {
                            // Confirma el pago
                            stripe.confirmPayment(
                                this@PaymentActivity,
                                ConfirmPaymentIntentParams.create(clientSecret)
                            )

                            // Termina el PaymentActivity después de confirmar el pago
                            showToast("Pago exitoso!")
                            Log.d("valores", "Pago Exitoso")

                            // Regresa el resultado del pago exitoso
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

            /**
             * Función que se ejecuta al fallar la petición
             * @param call: llamada de la petición
             * @param t: excepción generada
             * @author Jesús Guzmán | A01799257
             */
            override fun onFailure(call: Call<PaymentIntentResponse>, t: Throwable) {
                runOnUiThread {
                    showToast("Failed to create payment intent")
                    setResult(Activity.RESULT_CANCELED)
                    finish()
                }
            }
        })
    }

    /**
     * Función que muestra un mensaje en pantalla
     * @param message: mensaje a mostrar en pantalla
     * @return Toast: mensaje en pantalla con duración corta
     * @author Jesús Guzmán | A01799257
     */
    private fun showToast(message: String) {
        Toast.makeText(this@PaymentActivity, message, Toast.LENGTH_SHORT).show()
    }
}