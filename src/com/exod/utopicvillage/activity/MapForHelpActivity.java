/**
 * @author Roussel Guillaume
 * @licence CC-BY-NC
 */
package com.exod.utopicvillage.activity;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.asynchrone.GetNearAskingHelpAsync;
import com.exod.utopicvillage.entity.Help;
import com.exod.utopicvillage.entity.User;
import com.exod.utopicvillage.overlay.CustomOverlayItem;
import com.exod.utopicvillage.overlay.MapHelpOverlay;
import com.exod.utopicvillage.util.DateUtil;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class MapForHelpActivity extends TabMenuActivity{
	MapView mapView;
	private double matchLatitude;
	private double matchLongitude;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,R.layout.map_help);
		        
        //on centre la carte sur la position courante
        User user = utopicVillageApplication.getStorage().getUser();
        GeoPoint centreMap = new GeoPoint((int)(user.getLatitude()*1000000), (int)(user.getLongitude()*1000000));
        mapView = (MapView) findViewById(R.id.map_for_help);
        mapView.getController().setZoom(12);
        mapView.getController().setCenter(centreMap);
        mapView.getController().animateTo(centreMap);
        mapView.setSatellite(true);
        
        //we save the current coords
        matchLatitude = user.getLatitude();
        matchLongitude= user.getLongitude();
        
        //on place les controle de la map
        mapView.setBuiltInZoomControls(true);
        
        //on place les marqeurs alentours
        displayOverlays();
	}	
	
	public MapView getMapView() {
		return mapView;
	}

	public void setMapView(MapView mapView) {
		this.mapView = mapView;
	}

	public void displayOverlays(){
		//appel asynchrone
		GetNearAskingHelpAsync nearAskingAsync = new GetNearAskingHelpAsync(this);
		nearAskingAsync.execute();
		
	}
	
	public void displayPin(Collection<Help>colHelp){
		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.marker);
		MapHelpOverlay<CustomOverlayItem> itemizedoverlay = new MapHelpOverlay<CustomOverlayItem>(drawable, this);
		if(colHelp!=null){
			for (Iterator<Help> iterator = colHelp.iterator(); iterator.hasNext();) {
				Help help = (Help) iterator.next();
				GeoPoint point = new GeoPoint((int)(help.getUser().getLatitude()*1000000),(int)(help.getUser().getLongitude()*1000000));
				
				String stringDesc = help.getDescription();
				//on split la description dans le cas ou elle est trop longue
				if(stringDesc!=null && stringDesc.length()>50){
					stringDesc = help.getDescription().substring(0,50)+" ...";
				}
				CustomOverlayItem overlayitem = new CustomOverlayItem(point, getResources().getString(R.string.ask_for)+" "+DateUtil.convertToStringDifDate(help.getDate()), stringDesc,"http://ia.media-imdb.com/images/M/MV5BMjAyNjk5Njk0MV5BMl5BanBnXkFtZTcwOTA4MjIyMQ@@._V1._SX40_CR0,0,40,54_.jpg");
				overlayitem.setIdHelp(help.getId());
				
				itemizedoverlay.addOverlay(overlayitem);
				mapOverlays.add(itemizedoverlay);
			}
		}
	}
	
	public void startDetailHelpActivity(String idHelp){
		Intent intent = new Intent(this, DetailHelpActivity.class);
		intent.putExtra("idHelp", idHelp);
		startActivity(intent);
	}
	
	public void goSearch(View view){
		Log.d("MapForHelp","search ok");
		//we disabled the search button
		Button buttonSearch = (Button)findViewById(R.id.buttonSearch);
		buttonSearch.setClickable(false);
		buttonSearch.setEnabled(false);
		buttonSearch.setBackgroundResource(R.drawable.button_disabled);
		
		//we clear the map view and we request the server
		mapView.getOverlays().clear();
		
		//asynchronous call
		GetNearAskingHelpAsync nearAskingAsync = new GetNearAskingHelpAsync(this);
		nearAskingAsync.execute();
	}
	
//	@Override
//	public boolean onTouchEvent(MotionEvent me) {
//		Log.d("MapForHelp","touch ok");
//		if(Math.abs(mapView.getMapCenter().getLatitudeE6()-matchLatitude*1000000)>Constante.TOLERANCE_SEARCH || Math.abs(mapView.getMapCenter().getLongitudeE6()-matchLongitude*1000000)>Constante.TOLERANCE_SEARCH){
//			Log.d("MapForHelp","coord ok");
//			//we enabled the search button
//			Button buttonSearch = (Button)findViewById(R.id.buttonSearch);
//			buttonSearch.setClickable(true);
//			buttonSearch.setEnabled(true);
//			buttonSearch.setBackgroundResource(R.drawable.button_state);
//			//we set the new coord
//			matchLatitude = mapView.getMapCenter().getLatitudeE6()/1000000;
//			matchLongitude = mapView.getMapCenter().getLongitudeE6()/1000000;
//		}
//		return true;
//		//return super.onTouchEvent(me);
//	 }
	
	public void moveMap(View view){
		Log.d("touch","element carte touché");
	}
}
