/*
 *                    PDB web development code
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  If you do not have a copy,
 * see:
 *
 *      http://www.gnu.org/copyleft/lesser.html
 *
 * Copyright for this code is held jointly by the individual
 * authors.  These should be listed in @author doc comments.
 *
 *
 * Created on Aug 5, 2009
 * Created by ap3
 *
 */

package org.biojava.nbio.structure.secstruc;

import org.biojava.nbio.structure.AminoAcid;

/**
 * This class extends the basic container for secondary structure
 * annotation, including all the information used in the DSSP algorithm.
 * 
 * @author Andreas Prlic
 * @author Aleix Lafita
 *
 */
public class SecStrucState extends SecStrucInfo {
	
	private double phi;
	private double psi;
	private double omega;

	private float kappa;
	
	private HBond accept1;
	private HBond accept2;
	private HBond donor1;
	private HBond donor2;

	private boolean[] turn;
	private boolean bend;

	public SecStrucState(AminoAcid aa, String ass, SecStrucType t){
		super(aa, ass, t);
		
		phi = 360;
		psi = 360;
		omega = 360;
		
		accept1 = new HBond();
		accept2 = new HBond();
		donor1  = new HBond();
		donor2  = new HBond();
		
		turn = new boolean[3];
		turn[0] = false;
		turn[1] = false;
		turn[2] = false;
		bend = false;
		
		kappa = 360;
	}
	
	public boolean isBend() {
		return bend;
	}

	public void setBend(boolean bend) {
		this.bend = bend;
	}

	public float getKappa() {
		return kappa;
	}

	public void setKappa(float kappa) {
		this.kappa = kappa;
	}

	public boolean[] getTurn() {
		return turn;
	}

	public void setTurn(boolean[] turn) {
		this.turn = turn;
	}

	public HBond getAccept1() {
		return accept1;
	}
	public void setAccept1(HBond accept1) {
		this.accept1 = accept1;
	}
	public HBond getAccept2() {
		return accept2;
	}
	public void setAccept2(HBond accept2) {
		this.accept2 = accept2;
	}
	public HBond getDonor1() {
		return donor1;
	}
	public void setDonor1(HBond donor1) {
		this.donor1 = donor1;
	}
	public HBond getDonor2() {
		return donor2;
	}
	public void setDonor2(HBond donor2) {
		this.donor2 = donor2;
	}
	public double getPhi() {
		return phi;
	}
	public void setPhi(double phi) {
		this.phi = phi;
	}
	public double getPsi() {
		return psi;
	}
	public void setPsi(double psi) {
		this.psi = psi;
	}

	public double getOmega() {
		return omega;
	}

	public void setOmega(double omega) {
		this.omega = omega;
	}
	
	public String printDSSPline(int index) {
		
		StringBuffer buf = new StringBuffer();
		buf.append(index + 1).append(" ");
		buf.append(parent.getChainId()).append(" ");
		buf.append(parent.getPDBName()).append(" ");
		buf.append(parent.getResidueNumber().toString()).append("\t");

		boolean[] turns = getTurn();
		for (int t=0;t<3;t++){
			if (turns[t]) {
				buf.append('>');
			} else {
				buf.append(' ');
			}
		}

		buf.append(type);
		buf.append(" ");
		
		if ( isBend())
			buf.append('S');
		else 
			buf.append(" ");
		buf.append("                 ");

		int p1 = getAccept1().getPartner();
		if ( p1 != 0)
			p1 -= index;
		double e1 =  (getAccept1().getEnergy() / 1000.0);
		buf.append(String.format( "%6d,%4.1f",p1,e1));

		int p2 = donor1.getPartner();
		if ( p2 != 0)
			p2 -= index;
		double e2 = (donor1.getEnergy() / 1000.0);
		buf.append(String.format( "%6d,%4.1f",p2,e2 ));

		int p3 = accept1.getPartner() ;
		if ( p3 != 0)
			p3 -= index;
		double e3 =  (accept2.getEnergy() / 1000.0);
		buf.append(String.format( "%6d,%4.1f",p3,e3));

		int p4 = donor2.getPartner();
		if ( p4 != 0)
			p4 -= index;
		double e4 = (donor2.getEnergy() / 1000.0);
		buf.append(String.format( "%6d,%4.1f",p4,e4 ));
		
		buf.append("        ");
		buf.append(String.format("%6.1f",kappa));
		buf.append("       ");
		buf.append(String.format("%6.1f %6.1f %6.1f", phi,psi, omega));
		buf.append(System.getProperty("line.separator"));
		
		return buf.toString();
	}

}
