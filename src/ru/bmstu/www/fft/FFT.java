package ru.bmstu.www.fft;
//https://ru.wikibooks.org/wiki/Реализации_алгоритмов/Быстрое_преобразование_Фурье
public class FFT {
	private final double TwoPi = 6.283185307179586;
	private short[] AVal;
	private int Nvl;
	private double[] FTvl;
	private int Nft;
	
	
	public FFT() {
		
	}
	
	public void fft(final short[] inputSignal) {
		
		this.AVal = inputSignal;
//		this.Nvl = this.AVal.length;
		// должно быть равно количеству точек на графике.
		this.Nvl = 512;
		this.Nft = this.Nvl;
		this.FTvl = new double[this.Nft];
		
		int i, j, n, m, Mmax, Istp;
		  double Tmpr, Tmpi, Wtmp, Theta;
		  double Wpr, Wpi, Wr, Wi;
		  double[] Tmvl;

		  n = Nvl * 2; Tmvl = new double[n];

		  for (i = 0; i < n - 1; i+=2) {
		   Tmvl[i] = 0;
		   Tmvl[i+1] = (double)AVal[i/2];
		  }

		  i = 1; j = 1;
		  while (i < n) {
		    if (j > i) {
		      Tmpr = Tmvl[i]; Tmvl[i] = Tmvl[j]; Tmvl[j] = Tmpr;
		      Tmpr = Tmvl[i+1]; Tmvl[i+1] = Tmvl[j+1]; Tmvl[j+1] = Tmpr;
		    }
		    i = i + 2; m = Nvl;
		    while ((m >= 2) && (j > m)) {
		      j = j - m; m = m >> 1;
		    }
		    j = j + m;
		  }

		  Mmax = 2;
		  while (n > Mmax) {
		    Theta = -TwoPi / Mmax; Wpi = Math.sin(Theta);
		    Wtmp = Math.sin(Theta / 2); Wpr = Wtmp * Wtmp * 2;
		    Istp = Mmax * 2; Wr = 1; Wi = 0; m = 1;

		    while (m < Mmax) {
		      i = m; m = m + 2; Tmpr = Wr; Tmpi = Wi;
		      Wr = Wr - Tmpr * Wpr - Tmpi * Wpi;
		      Wi = Wi + Tmpr * Wpi - Tmpi * Wpr;

		      while (i < n) {
		        j = i + Mmax;
		        Tmpr = Wr * Tmvl[j] - Wi * Tmvl[j-1];
		        Tmpi = Wi * Tmvl[j] + Wr * Tmvl[j-1];

		        Tmvl[j] = Tmvl[i] - Tmpr; Tmvl[j-1] = Tmvl[i-1] - Tmpi;
		        Tmvl[i] = Tmvl[i] + Tmpr; Tmvl[i-1] = Tmvl[i-1] + Tmpi;
		        i = i + Istp;
		      }
		    }

		    Mmax = Istp;
		  }

		  for (i = 0; i < Nft; i++) {
		    j = i * 2; 
		    FTvl[i] = 2 * Math.sqrt(Math.pow(Tmvl[j],2) + Math.pow(Tmvl[j+1],2))/Nvl; 
//		    System.out.println(this.FTvl[i]);
		  }
		  
//		  this.FTvl = null;
	}
	
	public double[] getFTvl() {
		return this.FTvl;
	}
	
}
