package dataFitters;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

import jMEF.BregmanSoftClustering;
import jMEF.MixtureModel;
import jMEF.PVector;
import jMEF.UnivariateGaussian;
import tools.ExpectationMaximization1D;
import tools.KMeans;

public class GaussianDataFitter {

	double emLogLikelihood;//EM logLikelihood of this gauss mix model
	double bscLogLikelihood;//BSC logLikelihood of this gauss mix model
	MixtureModel mmc;// Expectation Maximization Mixture Model
	MixtureModel mmef;//Bregman soft clustering Mixture Model
	PVector[] dataPoints;
	/**
	 * Main function.
	 * @param args
	 */
	public GaussianDataFitter (PVector[] points,int n) {//fit the datapoints to a mixture of n gaussians 

		// Display
		String title = "";
		title += "+---------------------------------------------+\n";
		title += "| EM Gauss Fitter | (with K-means verification)\n";
		title += "+---------------------------------------------+\n";
		//

		// Variables
		dataPoints=points;
		Vector<PVector>[] clusters = KMeans.run(points, n);//intial estimation of n clusters 

	
		// Classical EM
		
		mmc = ExpectationMaximization1D.initialize(clusters);
		mmc = ExpectationMaximization1D.run(points, mmc);
		emLogLikelihood=mmc.getEMLogLikelihod();
		//System.out.println("Mixure model estimated using classical EM \n" + mmc + "\n");
		
		
		// Bregman soft clustering
		//MixtureModel mmef;
		mmef = BregmanSoftClustering.initialize(clusters, new UnivariateGaussian());
		mmef = BregmanSoftClustering.run(points, mmef);
		bscLogLikelihood=mmef.getBSCLogLikelihod();
		//System.out.println("Mixure model estimated using Bregman soft clustering \n" + mmef + "\n");

	}

	public double getEMLogLikelihood(){
		return emLogLikelihood;
	}
	
	public double getBSCLogLikelihood(){
		return bscLogLikelihood;
	}
	
	public MixtureModel getEMmodel(){
		return mmc;
	}
	
	public MixtureModel getBSCModel(){
		return mmef;
	}
}
