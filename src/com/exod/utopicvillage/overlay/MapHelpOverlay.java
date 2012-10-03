package com.exod.utopicvillage.overlay;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

import com.exod.utopicvillage.activity.MapForHelpActivity;

public class MapHelpOverlay<Item extends CustomOverlayItem> extends BalloonItemizedOverlay<CustomOverlayItem>{
	MapForHelpActivity mapActivity;
	private ArrayList<CustomOverlayItem> mOverlays = new ArrayList<CustomOverlayItem>();

	public MapHelpOverlay(Drawable defaultMarker, MapForHelpActivity mapActivity) {
		super(boundCenterBottom(defaultMarker),mapActivity.getMapView());
		this.mapActivity=mapActivity;
	}
	
	public void addOverlay(CustomOverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}

	@Override
	protected CustomOverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}
	
//	@Override
//	public boolean onTap(int index) {
//	  OverlayItem item = mOverlays.get(index);
//	  AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
//	  dialog.setTitle(item.getTitle());
//	  dialog.setMessage(item.getSnippet());
//	  dialog.show();
//	  //on set le milieu de la carte sur le point cliqué 
//	  mapForHelpActivity.getMapView().getController().setCenter(item.getPoint());
//	  mapForHelpActivity.getMapView().getController().animateTo(item.getPoint());
//	  return true;
//	}
	
	@Override
	protected boolean onBalloonTap(int index, CustomOverlayItem item) {
		//grace à l'id on peut retrouver l'object dans la hash table de l'application
		mapActivity.startDetailHelpActivity(item.getIdHelp()+"");
		return super.onBalloonTap(index, item);
	}
}
