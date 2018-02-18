package pharmacy.notifications;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class PharmacyNotification {
    
    public void addNotification(String form){
        
        Image image = new Image("/pharmacy/icons/64/add_user.png");
        
        Notifications notify = Notifications.create()
            .text(form + " Added Successfully")
            .title("Add")
            .graphic(new ImageView(image))
            .position(Pos.BOTTOM_RIGHT)
            .hideAfter(Duration.seconds(5));

        notify.show();
    }
    
    public void deleteNotification(String form){
        
        Image image = new Image("/pharmacy/icons/64/delete_user.png");
        
        Notifications notify = Notifications.create()
            .text(form + " Deleted Successfully")
            .title("Delete")
            .graphic(new ImageView(image))
            .position(Pos.BOTTOM_RIGHT)
            .hideAfter(Duration.seconds(5));

        notify.show();
    }
    
    public void editNotification(String form){
        
        Image image = new Image("/pharmacy/icons/64/edit_user.png");
        
        Notifications notify = Notifications.create()
            .text(form + " Edited Successfully")
            .title("Edit")
            .graphic(new ImageView(image))
            .position(Pos.BOTTOM_RIGHT)
            .hideAfter(Duration.seconds(5));

        notify.show();
    }
}
