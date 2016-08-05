/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tedkwan.javafem.beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Devils
 */

 
@Named(value = "pictureViewer")
@SessionScoped
public class PictureViewer implements Serializable {

    /**
     * Creates a new instance of PictureViewer
     */
    public PictureViewer() {
    }
        private List<String> images;
     
    @PostConstruct
    public void init() {
        images = new ArrayList<>();
        images.add("Ted_Grad_Pose.jpg");
        images.add("Ted_Grad_Pose_Big.jpg");
        images.add("Ted_UCI_Seal.jpg");
        images.add("Ted_With_Dean.jpg");
        images.add("Ted_With_Peter.jpg");
        images.add("Ted_With_PeterBW.jpg");
        images.add("UCI_Ceremony.jpg");
    }
 
    public List<String> getImages() {
        return images;
    }
}
