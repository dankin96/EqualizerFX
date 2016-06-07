package ru.bmstu.www.effects;


public class Echo extends Effect {
	
	private int echo;
	private int maxD = 25000;
	private int minD = 13000;
	private double coef = 1.0;
	
	public Echo() {
		super(); 
		this.echo = 17000;
	}
	
	public void setInputSampleStream(short[] inputAudioStream) {
		this.inputAudioStream = inputAudioStream;		
	}

	@Override
	public synchronized short[] createEffect() {
		
		short amplitude = 0;
		short echoAmplitude = 0;	
		int checkFlag = 0;
		int position = 0;
		
		for(int i = echo ; i < this.inputAudioStream.length; i++) {
			amplitude = this.inputAudioStream[i];
			echoAmplitude = this.inputAudioStream[position];
			checkFlag = (((echoAmplitude) + (int)(0.9 * amplitude)));
			if(checkFlag < Short.MAX_VALUE && checkFlag > Short.MIN_VALUE) { 
				echoAmplitude = (short)checkFlag;
				this.inputAudioStream[position] =  echoAmplitude; 
				position += 1;                    
			} 			
		}
		return this.inputAudioStream;
	}
		
	public void setEchoCoef(double coef) {
		this.coef = coef;
		this.echo *= this.coef;
		if(this.echo > this.maxD)
			this.echo = this.maxD;
		if(this.echo < this.minD)
			this.echo = this.minD;
	}

	@Override
	public synchronized short[] getOutputAudioStream() {
		return this.inputAudioStream;
	}

}
