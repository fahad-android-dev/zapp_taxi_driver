import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.zapp_taxi_driver.R
import com.example.zapp_taxi_driver.mvvm.bookings.view.BookingDetailsObj
import com.example.zapp_taxi_driver.mvvm.home.view.HomeActivity
import com.example.zapp_taxi_driver.mvvm.login.view.LoginActivity
import com.example.zapp_taxi_driver.mvvm.register.view.RegisterActivity


object AppNavigation {
    fun Context.navigateToHome(msg: String = "", block: () -> Unit) {
        val intent = Intent(this, HomeActivity::class.java)
        if (msg.isNotEmpty()) intent.putExtra("msg", msg)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        block()
    }

    fun Context.navigateToLogin(clearBackStack: Boolean = false) {
        val intent = Intent(this, LoginActivity::class.java)
        if (clearBackStack) intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    fun Context.navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }


    fun Activity.backStackWithIntent(intent: Intent , resultCode:Int = Activity.RESULT_OK) {
        setResult(resultCode, intent)
        finish()
    }
    fun Fragment.navigateToBookingDetails(bookingDetailsObj: BookingDetailsObj? = null) {
        val bundle = Bundle()
        if (bookingDetailsObj != null) bundle.putSerializable("bookingDetailsObj", bookingDetailsObj)
        findNavController().navigate(R.id.action_to_navigation_booking_details, bundle)
    }

}