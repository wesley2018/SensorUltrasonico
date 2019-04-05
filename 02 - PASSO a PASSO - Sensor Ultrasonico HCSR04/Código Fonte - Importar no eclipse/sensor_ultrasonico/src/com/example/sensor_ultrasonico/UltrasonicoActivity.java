/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/

package com.example.sensor_ultrasonico; // NOME DO PACOTE REFERENTE A CLASSE
/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/

//IMPORTA��O DAS BIBLIOTECAS NECESS�RIAS PARA EXECU��O DO C�DIGO

import java.text.DecimalFormat;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class UltrasonicoActivity extends ActionBarActivity implements OnClickListener {
	/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
	// DECLARA��O DE VARI�VEIS
	TextView tvDistancia;
	EditText et_Ip;
	ImageButton btConectar;
	String L, hostIp = null;
	Handler mHandler;
	long lastPress;
	
	// M�TODO QUE CRIA A PRIMEIRA TELA DA APLICA��O
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		telaIp(); // FAZ A CHAMADA DO M�TODO "telaIp"
	}
	// M�TODO "telaIp"
	public void telaIp(){
		setContentView(R.layout.tela_ip); // INICIALIZA A TELA
		et_Ip = (EditText)findViewById(R.id.et_Ip); // ESTANCIA O EDITTEXT
		
    	btConectar = (ImageButton) findViewById(R.id.btConectar); // ESTANCIA O IMAGEBUTTON
        btConectar.setOnClickListener(this); // ATIVA O CLICK DO BOT�O
    	
    	if(btConectar.isPressed()){ // SE O BOT�O FOR PRESSIONADO
    		onClick(btConectar); // EXECUTA A FUN��O REFERENTE AO BOT�O
    	}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
    }
	// M�TODO "telaPrincipal"
	public void telaPrincipal(){
		setContentView(R.layout.activity_ultrasonico); // INICIALIZA A TELA
		/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		mHandler = new Handler(); // VARI�VEL "mHandler" INICIALIZADA
        mHandler.post(mUpdate);	 // VARI�VEL "mHandler" CHAMA O M�TODO "mUpdate"
		
        tvDistancia = (TextView) findViewById(R.id.tvDistancia); // ESTANCIA O TEXTVIEW
		
	}
	// M�TODO QUE EXECUTA A ATUALIZA��O DO TEXTVIEW COM INFORMA��O RECEBIDAS DO ARDUINO
	private Runnable mUpdate = new Runnable() {
    	public void run() {
    		arduinoStatus("http://"+hostIp+"/?L=1"); // CHAMA O M�TODO "arduinoStatus" E PASSA O PAR�METRO ENTRE "PAR�NTESES"
    		mHandler.postDelayed(this, 500); // TEMPO DE INTERVALO PARA ATUALIZAR NOVAMENTE A INFORMA��O (500 MILISEGUNDOS)
    	}
    };/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
    // M�TODO "arduinoStatus"
    public void arduinoStatus(String urlArduino){
    	/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/                                                                                                                          /**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		String urlHost = urlArduino; // CRIA UMA STRING CHAMADA
		String respostaRetornada = null; // CRIA UMA STRING CHAMADA "respostaRetornada" QUE POSSUI VALOR NULO
		
		//INICIO DO TRY CATCH
		try{
			respostaRetornada = ConectHttpClient.executaHttpGet(urlHost); // STRING "respostaRetornada" RECEBE RESPOSTA RETORNADA PELO ARDUINO
			String resposta = respostaRetornada.toString(); // STRING "resposta" 
			resposta = resposta.replaceAll("\\s+", "");
			
			String[] b = resposta.split(","); // O VETOR "String[] b" RECEBE  O VALOR DE "resposta.split(",")"
			/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
			int resultado = Integer.parseInt(b[0]); // VARI�VEL INTEIRA "resultado" RECEBE A POSI��O 1 DO VETOR
			double result; // CRIA��O DA VARI�VEL "result" DO TIPO DOUBLE
			DecimalFormat df = new DecimalFormat("0.00"); // FUN��O LIMITA A VARI�VEL A SOMENTE 2 CASAS DECIMAIS AP�S A VIRGULA
			
			result = resultado * 0.01 ; // VARI�VEL "result" RECEBE "resultado" MULTIPLICADO POR 0.01 (CONVERS�O DE CM EM METROS)
			String medida = df.format(result); // STRNG MEDIDA RECEBE "result"
			if(result < 4){ // SE "result" FOR MENOR QUE 4
				tvDistancia.setText(medida+"m"); // O TEXTVIEW RECEBE O VALOR RETORNADO PELO ARDUINO
			}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		}	
		catch(Exception erro){  // FUN��O DE EXIBI��O DO ERRO
		}// FIM DO TRY CATCH
	}
    // M�TODO QUE VERIFICA O BOT�O DE VOLTAR DO DISPOSITIVO ANDROID E ENCERRA A APLICA��O SE PRESSIONADO 2 VEZES SEGUIDAS
    public void onBackPressed() {
		
	    long currentTime = System.currentTimeMillis();
	    if(currentTime - lastPress > 5000){
	        Toast.makeText(getBaseContext(), "Pressione novamente para sair.", Toast.LENGTH_LONG).show();
	        lastPress = currentTime;	        
	    }else{/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
	        super.onBackPressed();
	        android.os.Process.killProcess(android.os.Process.myPid());
	    }
	}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
	@Override
	public void onClick(View bt) { // M�TODO QUE GERENCIA OS CLICK'S NOS BOT�ES
	
		if(bt == btConectar){ // SE BOT�O CLICKADO
			if(et_Ip.getText().toString().equals("")){ // SE EDITTEXT ESTIVER VAZIO
			Toast.makeText(getApplicationContext(), // FUN��O TOAST
			"Digite o IP do Ethernet Shield!", Toast.LENGTH_SHORT).show(); // EXIBE A MENSAGEM
			}else{ // SEN�O
				/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
				hostIp = et_Ip.getText().toString(); // STRING "hostIp" RECEBE OS DADOS DO EDITTEXT CONVERTIDOS EM STRING
				// FUN��O QUE OCULTA O TECLADO AP�S CLICAR EM CONECTAR
				InputMethodManager escondeTeclado = (InputMethodManager)getSystemService(
			    Context.INPUT_METHOD_SERVICE);
			    escondeTeclado.hideSoftInputFromWindow(et_Ip.getWindowToken(), 0);
				telaPrincipal(); // FAZ A CHAMADA DO M�TODO "telaPrincipal"
			}	
		}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
	}
}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/