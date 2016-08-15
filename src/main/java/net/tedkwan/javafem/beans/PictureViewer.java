
package net.tedkwan.javafem.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Managed Bean for the picture gallery.
 *
 *
 * This managed bean is the bean which creates a list of photos so
 * that the pictures can be viewed in JSF.
 *
 * @author Ted Kwan
 */
@ManagedBean(name = "pictureViewer")
@ViewScoped
public class PictureViewer implements Serializable {

    /**
     * Creates a new instance of PictureViewer
     */
    public PictureViewer() {
    }
        private List<String> images;
        
    /**
     * Initializes the picture viewer by loading the pictures
     * that are going to be displayed.
     */
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
