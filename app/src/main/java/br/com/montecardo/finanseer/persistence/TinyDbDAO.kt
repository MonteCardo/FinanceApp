package br.com.montecardo.finanseer.persistence

import android.content.Context
import br.com.montecardo.finanseer.investment.data.Investment
import br.com.montecardo.finanseer.investment.data.Movement
import br.com.montecardo.finanseer.investment.data.SimpleInvestment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mukesh.tinydb.TinyDB

/**
 * DAO implementation for TinyDB. Saves of SharedPreferences
 *
 * Created by gabryel on 20/09/17.
 */
class TinyDbDAO(application: Context) : FinanceDAO {

    private val invType = object : TypeToken<MutableMap<String, SimpleInvestment>>() {}

    private val movType = object : TypeToken<MutableMap<String, Movement>>() {}

    private val INV_KEY = "investment"

    private val MOV_KEY = "movement"

    private val gson = Gson()

    private val db: TinyDB = TinyDB(application)

    private val movements: MutableMap<String, Movement>

    private val investments: MutableMap<String, Investment>

    init {
        if (!db.contains(INV_KEY)) {
            db.putObject(INV_KEY, mapOf<String, Investment>(), gson)
        }

        investments = db.getObject(INV_KEY, invType, gson).toMutableMap()

        if (!db.contains(MOV_KEY)) {
            db.putObject(MOV_KEY, mapOf<String, Movement>(), gson)
        }

        movements = db.getObject(MOV_KEY, movType, gson).toMutableMap()
    }

    override fun persist(inv: Investment) {
        investments[inv.id] = inv
        db.putObject(INV_KEY, investments, Gson())
    }

    override fun persist(mov: Movement) {
        movements[mov.id] = mov
        db.putObject(MOV_KEY, movements, Gson())
    }

    override fun getInvestment(id: String) = investments[id]

    override fun getAllInvestments() = investments.values.toList()

    override fun getMovementsByInvestment(invId: String) =
            movements.values.filter { it.investmentId == invId }
}