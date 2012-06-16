package com.exod.utopicvillage.activity;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.MotionEvent;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.entity.Help;
import com.exod.utopicvillage.entity.User;
import com.exod.utopicvillage.listener.PositionListener;
import com.exod.utopicvillage.overlay.CustomOverlayItem;
import com.exod.utopicvillage.overlay.MapHelpOverlay;
import com.exod.utopicvillage.util.DateUtil;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class MapForHelpActivity extends TabMenuActivity implements OnDoubleTapListener {
	MapView mapView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,R.layout.map_help);
		
		
		//on set le location manager
		//TODO
		//asynch task
		LocationManager objgps = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        PositionListener positionListener = new PositionListener();
        //on lui permet d'utiliser les element de l'application
        positionListener.setWebService(utopicVillageApplication);
        objgps.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, positionListener);
        
        //on centre la carte sur la position courante
        User user = utopicVillageApplication.getStorage().getUser();
        GeoPoint centreMap = new GeoPoint((int)(user.getLatitude()*1000000), (int)(user.getLongitude()*1000000));
        mapView = (MapView) findViewById(R.id.map_for_help);
        mapView.getController().setZoom(5);
        mapView.getController().setCenter(centreMap);
        mapView.getController().animateTo(centreMap);
        
        //on place les controle de la map
        mapView.setBuiltInZoomControls(true);
        
        //on place les marqeurs alentours
        displayOverlays();
	}

	@Override
	public boolean onDoubleTap(MotionEvent arg0) {
		mapView.getController().zoomIn();
		return false;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent arg0) {
		return false;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent arg0) {
		return false;
	}
	
	public MapView getMapView() {
		return mapView;
	}

	public void setMapView(MapView mapView) {
		this.mapView = mapView;
	}

	public void displayOverlays(){
		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.marker);
		MapHelpOverlay<CustomOverlayItem> itemizedoverlay = new MapHelpOverlay<CustomOverlayItem>(drawable, this);
		
		Collection<Help> colHelp = webService.getNearAskingHelp();
		for (Iterator<Help> iterator = colHelp.iterator(); iterator.hasNext();) {
			Help help = (Help) iterator.next();
			GeoPoint point = new GeoPoint((int)(help.getUser().getLatitude()*1000000),(int)(help.getUser().getLongitude()*1000000));
			CustomOverlayItem overlayitem = new CustomOverlayItem(point, getResources().getString(R.string.ask_for)+DateUtil.convertToStringDifDate(help.getDate()), help.getDescritpion(),"http://ia.media-imdb.com/images/M/MV5BMjAyNjk5Njk0MV5BMl5BanBnXkFtZTcwOTA4MjIyMQ@@._V1._SX40_CR0,0,40,54_.jpg");
			overlayitem.setIdHelp(help.getId());
			
			itemizedoverlay.addOverlay(overlayitem);
			mapOverlays.add(itemizedoverlay);
		}	
	}
	
	public void startDetailHelpActivity(String idHelp){
		Intent intent = new Intent(this, DetailHelpActivity.class);
		intent.putExtra("idHelp", idHelp);
		startActivity(intent);
	}
}