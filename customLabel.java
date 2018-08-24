import javax.swing.*;
import java.awt.*;

//creates label with custom font
public class customLabel extends JLabel {
    private Font comicSans = new Font("Comic Sans MS", Font.PLAIN, 20);
    public customLabel(Integer x)
    {
        super(x.toString());
        this.setFont(comicSans);

    }

    public customLabel()
    {
        super("");
        this.setFont(comicSans);
    }

    public void setText(Integer text) {
        super.setText(text.toString());
    }
}
