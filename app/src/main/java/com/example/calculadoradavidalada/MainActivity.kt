package com.example.calculadoradavidalada

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.slider.RangeSlider

class MainActivity : AppCompatActivity() {

    //Creamos variables privadas
    private lateinit var etSalary: EditText
    private lateinit var rsHeight: RangeSlider
    private lateinit var etKids: EditText
    private lateinit var etDisc: EditText
    private lateinit var etAge: EditText
    private lateinit var etEstadoCivil: EditText
    private lateinit var etGrupoProfesional: EditText
    private lateinit var tvNumPagas: TextView
    private var currentHeight: Int = 0
    private lateinit var goButton: Button


    //Creamos los atributos necesarios para logica
    private var grossSalary: Int = 0
    private var nPagas: Int = 12
    private var nKids: Int = 0
    private var nDisc: Int = 0
    private var nAge: Int = 16
    private var nEstadoCivil: String = ""
    private var nGrupoProfesional: String = ""
    private var nCurrentHeight: Int = 0

    //Creacion de un companion objetc que es accesible desde todas las activities
    companion object {
        const val SALARY_KEY = "RESULT_SALARY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Nuestro codigo en el OnCreate

        //Para iniciar los componentes visuales
        initComponents()

        //Parara iniciar los listeners de los eventos
        initListeners()

        //Configuraciones visuales de los componentes
        initUI()
    }

    private fun initComponents() {
        this.etSalary = findViewById((R.id.etSalary))
        this.rsHeight = findViewById((R.id.rsHeight))
        this.tvNumPagas = findViewById(R.id.tvNumPagas)
        this.etKids = findViewById((R.id.etKids))
        this.etDisc = findViewById((R.id.etDisc))
        this.etAge = findViewById((R.id.etAge))
        this.etEstadoCivil = findViewById((R.id.etEstadoCivil))
        this.etGrupoProfesional = findViewById((R.id.etGrupoProfesional))
        this.goButton = findViewById((R.id.goButton))

    }

    private fun initUI() {
        rsHeight.setValues(12f)
        tvNumPagas.text = rsHeight.values[0].toInt().toString()
        etSalary.setText("0")
        etKids.setText("0")
        etDisc.setText("0")
        etAge.setText("16")
        etEstadoCivil.setText("soltero")
        etGrupoProfesional.setText("Ingeniero")
        goButton.isEnabled = true
    }

    private fun initListeners() {
        this.rsHeight.addOnChangeListener { slider, value, _ ->
            this.currentHeight = value.toInt()
            this.tvNumPagas.text = currentHeight.toString()
        }

        this.goButton.setOnClickListener {
            calculo()
        }
    }

    /**
    private fun navigateToResult(resultSalary: Double) {
        //Creamos el objeto intent
        val intent = Intent(this, activity_result_calculadora::class.java)

        //Añadimos el extra para pasar el resultSalary
        intent.putExtra(SALARY_KEY, resultSalary)

        this.startActivity(intent)
    }
    **/


        private fun calcularRetencion(etGrupoProfesional: String): Double {
            val baseRetencion = when (etGrupoProfesional) {
                "1" -> 0.20
                "2" -> 0.15
                "3" -> 0.10
                else -> 0.12
            }
            return baseRetencion
        }

    private fun calcularDeducciones(nAge: Int, nDisc: Int, nEstadoCivil: String, nKids: Int): Double {
        var reduccion = 0.0

        if (nDisc > 33) {
            reduccion += 0.05
        }

        if (nEstadoCivil.equals("casado", ignoreCase = true)) {
            reduccion += 0.03
        }

        reduccion += nKids * 0.01

        if (nAge > 65) {
            reduccion += 0.02
        }

        return reduccion
    }

    private fun calculateNetSalary(grossSalary: Int, retencion: Double, deducciones: Double): Double {
        val retencionFinal = (retencion - deducciones).coerceAtLeast(0.0)
        val salarioFinal = (grossSalary - (retencionFinal * grossSalary)).coerceAtLeast(0.0)
        return salarioFinal
    }

    private fun calculo(){
        grossSalary = this.etSalary.text.toString().toIntOrNull() ?: 0
        nPagas = this.tvNumPagas.text.toString().toIntOrNull() ?: 12
        nKids = this.etKids.text.toString().toIntOrNull() ?: 0
        nDisc = this.etDisc.text.toString().toIntOrNull() ?: 0
        nAge = this.etAge.text.toString().toIntOrNull() ?: 16
        nEstadoCivil = this.etEstadoCivil.text.toString()
        nGrupoProfesional = this.etGrupoProfesional.text.toString()

        val retencion = calcularRetencion(nGrupoProfesional)
        val deducciones = calcularDeducciones(nAge, nDisc, nEstadoCivil, nKids)
        val salarioNeto = calculateNetSalary(grossSalary, retencion, deducciones)
        val salarioBruto = grossSalary.toDouble()

        val intent = Intent(this, result_calculadora::class.java)
        intent.putExtra("salarioBruto", salarioBruto.toString())
        intent.putExtra("salarioNeto", salarioNeto.toString())
        intent.putExtra("deducciones", (deducciones * grossSalary).toString())
        intent.putExtra("retencion", (retencion * grossSalary).toString())
        startActivity(intent)


    }

}






