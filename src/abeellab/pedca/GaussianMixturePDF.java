package abeellab.pedca;
import jMEF.MixtureModel;
import jMEF.PVector;;



public class GaussianMixturePDF {
	double [] yDataPoints;
	double [] xDataPoints;
	double [] mus;
	double [] sigmas;
	double beg;//bgining of x axis
	double end;//end of x axis
	double step;//step on x axis
    double maxYvalue=0.0;
    MixtureModel mixtMod;
    
	public GaussianMixturePDF(MixtureModel mm,double beg,double end){
		step=((end-beg)/200);//    /500);
		mixtMod=mm;
		//System.out.println("GaussianMixturePDF beg:"+beg+" end:"+end+" step:"+step+" xdatapoint size:"+((int) ((end-beg)/step)));
		mus=new double[mm.size];
		sigmas=new double[mm.size];
		this.beg=beg;
		this.end=end;
	
		yDataPoints=new double[(int) ((end-beg)/step)];
		xDataPoints=new double[(int) ((end-beg)/step)];
		
	
		for (int p=0;p<mm.size;p++){//for each param extract mu and sigma
			mus[p]=((jMEF.PVector)mm.param[p]).array[0];	
			sigmas[p]=((jMEF.PVector)mm.param[p]).array[1];	
		}
		double currentY;
		
		double dp=beg;//datapoint
		int dpInd=0;

		while (dp<(end-step)){//for each datapoint
			currentY=0.0;
			
			for(int mixtElem=0;mixtElem<mm.size;mixtElem++){//for each mixture member
				currentY+=mm.weight[mixtElem]*pdf(dp,mus[mixtElem],sigmas[mixtElem]);		
			}
			//if(dp>30 && dp<50)
			
			xDataPoints[dpInd]=dp;
			yDataPoints[dpInd++]=currentY;
			if (currentY>maxYvalue)maxYvalue=currentY;
			//System.out.println("dp:"+(dpInd-1)+" dpx:"+dp+" dpy:"+currentY);
			dp+=step;
		}

	}
	
	
	// return pdf(x) = standard Gaussian pdf
    public static double pdf(double x) {
        return Math.exp(-x*x / 2) / Math.sqrt(2 * Math.PI);
    }

    // return pdf(x, mu, signma) = Gaussian pdf with mean mu and stddev sigma
    public static double pdf(double x, double mu, double sigma) {
        return phi((x - mu) / sigma) / sigma;
    }

    // return phi(x) = standard Gaussian pdf
    public static double phi(double x) {
        return pdf(x);
    }



    // return phi(x, mu, signma) = Gaussian pdf with mean mu and stddev sigma

    public static double phi(double x, double mu, double sigma) {
        return pdf(x, mu, sigma);
    }



 

}
