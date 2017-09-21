package br.com.montecardo.finanseer

import android.app.Application
import br.com.montecardo.finanseer.persistence.SingletonDAO
import br.com.montecardo.finanseer.persistence.TinyDbDAO
import com.jakewharton.threetenabp.AndroidThreeTen
import com.squareup.leakcanary.LeakCanary

/**
 * Created by gabryel on 15/06/17.
 */
class AppLoader : Application() {
    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }

        SingletonDAO.useProvider(TinyDbDAO(this))
        AndroidThreeTen.init(this)
        LeakCanary.install(this)
    }
}