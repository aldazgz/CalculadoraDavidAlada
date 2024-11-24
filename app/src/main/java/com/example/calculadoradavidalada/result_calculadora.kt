package com.example.calculadoradavidalada

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class result_calculadora : AppCompatActivity() {

    private lateinit var vSalBruDin: TextView
    private lateinit var vSalNetDin: TextView
    private lateinit var vIRPFdin: TextView
    private lateinit var vDedDin: TextView
    private lateinit var boton2: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result_calculadora)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        /**
        val resultSalary = intent.extras?.getDouble(SALARY_KEY)?: -1.0
         **/

        initComponents()

        initListenners()

        initUI()


    }

    private fun initUI() {
        val salarioBruto = intent.extras?.getString("salarioBruto").orEmpty()
        val salarioNeto = intent.extras?.getString("salarioNeto").orEmpty()
        val retencion = intent.extras?.getString("retencion").orEmpty()
        val deducciones = intent.extras?.getString("deducciones").orEmpty()

        this.vSalBruDin.text = "$salarioBruto €"
        this.vSalNetDin.text = "$salarioNeto €"
        this.vIRPFdin.text = "$retencion €"
        this.vDedDin.text = "$deducciones €"

    }

    private fun initListenners() {
        this.boton2.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initComponents() {
        this.vSalBruDin = findViewById((R.id.tvSalBruDin))
        this.vSalNetDin = findViewById((R.id.tvSalNetDin))
        this.vDedDin = findViewById((R.id.tvDedDin))
        this.vIRPFdin = findViewById((R.id.tvIRPFdin))
        this.boton2 = findViewById((R.id.boton2))

    }
}