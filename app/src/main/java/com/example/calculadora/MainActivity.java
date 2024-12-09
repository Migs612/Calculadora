package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textoResultado;
    private double primerOperando = 0;
    private String operador = "";
    private boolean nuevaOperacion = true;
    private boolean decimalPuesto = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoResultado = findViewById(R.id.textoResultado);

        int[] idsBotonesNumeros = {R.id.boton0, R.id.boton1, R.id.boton2, R.id.boton3, R.id.boton4, R.id.boton5, R.id.boton6, R.id.boton7, R.id.boton8, R.id.boton9};
        for (int id : idsBotonesNumeros) {
            Button boton = findViewById(id);
            boton.setOnClickListener(this::onNumeroClick);
        }

        Button botonDecimal = findViewById(R.id.botonDecimal);
        botonDecimal.setOnClickListener(this::onDecimalClick);


        int[] idsBotonesOperaciones = {R.id.botonSumar, R.id.botonRestar, R.id.botonMultiplicar, R.id.botonDividir, R.id.botonPorcentaje, R.id.botonRaiz, R.id.botonCuadrado};
        for (int id : idsBotonesOperaciones) {
            Button boton = findViewById(id);
            boton.setOnClickListener(this::onOperacionClick);
        }

        Button botonSigno = findViewById(R.id.botonSigno);
        botonSigno.setOnClickListener(this::onSignoClick);

        Button botonLimpiar = findViewById(R.id.botonLimpiar);
        botonLimpiar.setOnClickListener(this::onLimpiarClick);
        Button botonCE = findViewById(R.id.botonCE);
        botonCE.setOnClickListener(this::onCEClick);
        Button botonBorrar = findViewById(R.id.botonBorrar);
        botonBorrar.setOnClickListener(this::onBorrarClick);

        Button botonIgual = findViewById(R.id.botonIgual);
        botonIgual.setOnClickListener(this::onIgualClick);
    }


    private void onNumeroClick(View view) {
        Button button = (Button) view;
        String numero = button.getText().toString();
        if (nuevaOperacion) {
            textoResultado.setText(numero);
            nuevaOperacion = false;
        } else {
            textoResultado.append(numero);
        }
        decimalPuesto = false;
    }

    private void onDecimalClick(View view) {
        if (!decimalPuesto) {
            textoResultado.append(".");
            decimalPuesto = true;
        }
    }

    private void onOperacionClick(View view) {
        Button button = (Button) view;
        String operacion = button.getText().toString();

        if (!textoResultado.getText().toString().isEmpty()) {
            try {
                double num = Double.parseDouble(textoResultado.getText().toString());
                switch (operacion) {
                    case "+":
                    case "−":
                    case "×":
                    case "÷":
                        primerOperando = num;
                        operador = operacion;
                        nuevaOperacion = true;
                        break;
                    case "%":
                        textoResultado.setText(String.valueOf(num / 100));
                        nuevaOperacion = true;
                        break;
                    case "²√x":
                        if (num >= 0) {
                            textoResultado.setText(String.valueOf(Math.sqrt(num)));
                        } else {
                            textoResultado.setText("Error");
                        }
                        nuevaOperacion = true;
                        break;
                    case "xⁿ":
                        primerOperando = num;
                        operador = operacion;
                        nuevaOperacion = true;
                        break;
                    default:
                        break;
                }
            } catch (NumberFormatException e) {
                textoResultado.setText("Error");
            }
            decimalPuesto = false;
        }
    }

    private void onIgualClick(View view) {
        if (!operador.isEmpty() && !textoResultado.getText().toString().isEmpty()) {
            try {
                double segundoOperando = Double.parseDouble(textoResultado.getText().toString());
                double resultado = calcularResultado(primerOperando, segundoOperando, operador);
                textoResultado.setText(String.valueOf(resultado));
                operador = "";
                nuevaOperacion = true;
                primerOperando = resultado;
                decimalPuesto = false;
            } catch (NumberFormatException e) {
                textoResultado.setText("Error");
            }
        }
    }

    private double calcularResultado(double op1, double op2, String operador) {
        switch (operador) {
            case "+": return op1 + op2;
            case "−": return op1 - op2;
            case "×": return op1 * op2;
            case "÷":
                if (op2 == 0) {
                    textoResultado.setText("Error");
                    return 0;
                }
                return op1 / op2;
            case "%": return op1 / 100;
            case "²√x": return Math.sqrt(op1);
            case "xⁿ":
                if (op2 == 0) return 1;
                double resultado = 1;
                for (int i = 0; i < op2; i++) { resultado *= op1; }
                return resultado;
            default: return op1;
        }
    }

    private void onLimpiarClick(View view) {
        textoResultado.setText("0");
        primerOperando = 0;
        operador = "";
        nuevaOperacion = true;
        decimalPuesto = false;
    }

    private void onCEClick(View view) {
        textoResultado.setText("0");
        nuevaOperacion = true;
        decimalPuesto = false;
    }

    private void onBorrarClick(View view) {
        String texto = textoResultado.getText().toString();
        if (texto.length() > 1) {
            textoResultado.setText(texto.substring(0, texto.length() - 1));
        } else {
            textoResultado.setText("0");
        }
        if(texto.length() <=1 && texto.contains(".")){
            decimalPuesto = false;
        }
    }

    private void onSignoClick(View view) {
        String texto = textoResultado.getText().toString();
        if (!texto.isEmpty()) {
            try {
                double numero = Double.parseDouble(texto);
                textoResultado.setText(String.valueOf(-numero));
            } catch (NumberFormatException e) {
                textoResultado.setText("Error");
            }
        }
    }
}