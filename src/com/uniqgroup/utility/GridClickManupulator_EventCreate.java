package com.uniqgroup.utility;

import java.util.ArrayList;

import com.uniqgroup.application.R;

public class GridClickManupulator_EventCreate {

	int grid_selected_ID=0; //grid Id 
	/** 1. ONE TAP
	 *  2. LONG CLICK
	 */
	int mode=0;
	ArrayList<Integer> longclick_deleteGrid_list=new ArrayList<>();
	ArrayList<Integer> longclick_deleteResorce_id_list=new ArrayList<>();
	
	public int getGrid_selected_ID() {
		return grid_selected_ID;
	}
	public void setGrid_selected_ID(int grid_selected_ID) {
		this.grid_selected_ID = grid_selected_ID;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	
	/**LIST RELATED WORKS **/
	public void clearList(){
		longclick_deleteGrid_list.clear();
		longclick_deleteResorce_id_list.clear();
		mode=0;
	}
	public void addTo_List(int id){
		longclick_deleteGrid_list.add(getClickedNumber(id));
		longclick_deleteResorce_id_list.add(id);
	}
	public ArrayList<Integer> getDeleteable_list(){
		return longclick_deleteResorce_id_list;
	}
	
	/**THIS FUNCTION RETURNS THE NUMBER OF GRID IF ID IS GIVEN **/
	public int getClickedNumber(int id){
		int number=0;
		switch (id) {

		case R.id.event_seq1:
			number=1;
			break;
		case R.id.event_seq2:
			number=2;
			break;
		case R.id.event_seq3:
			number=3;
			break;
		case R.id.event_seq4:
			number=4;
			break;
		case R.id.event_seq5:
			number=5;
			break;
		case R.id.event_seq6:
			number=6;
			break;
		case R.id.event_seq7:
			number=7;
			break;
		case R.id.event_seq8:
			number=8;
			break;
		case R.id.event_seq9:
			number=9;
			break;
		case R.id.event_seq10:
			number=10;
			break;
		case R.id.event_seq11:
			number=11;
			break;
		case R.id.event_seq12:
			number=12;
			break;

		default:
			break;
		}
		return number;
	}
	
	/** HALT ALL OPERATION ON FLING**////
	public void halt_on_fling(){
		grid_selected_ID=0;
		mode=0;
	}
	
	
	
	
}
