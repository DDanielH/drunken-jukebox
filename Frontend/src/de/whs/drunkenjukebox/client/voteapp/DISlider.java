package de.whs.drunkenjukebox.client.voteapp;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import com.kiouri.sliderbar.client.view.SliderBarHorizontal;

public class DISlider extends SliderBarHorizontal {

	   ImagesKDEHorizontalLeftBW images = GWT.create(ImagesKDEHorizontalLeftBW.class);
       
       public DISlider(int maxValue, String width) {
           setLessWidget(new Image(images.less()));
           //setMoreWidget(new Image(images.more()));
           setScaleWidget(new Image(images.scale().getSafeUri()), 5);
           setMoreWidget(new Image(images.more()));
           setDragWidget(new Image(images.drag()));
           this.setWidth(width);
           this.setMaxValue(maxValue);         
       }
               
       interface ImagesKDEHorizontalLeftBW extends ClientBundle {
               
               @Source("kdehdrag.png")
               ImageResource drag();

               @Source("kdehless.png")
               ImageResource less();

               @Source("kdehmore.png")
               ImageResource more();

               @Source("kdehscale.png")
               DataResource scale();
       }       
	
}