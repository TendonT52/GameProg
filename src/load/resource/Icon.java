package load.resource;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Icon {

	public BufferedImage[] ore_icon;
	public BufferedImage fuel_icon;
	public BufferedImage money_icon;
	public BufferedImage title;
	public BufferedImage goal_icon;

	public Icon() {
		loadTitle();
		loadFuelIcon();
		loadMoneyIcon();
		loadGoalIcon();
		loadIcon();
	}

	private BufferedImage loadOtherIcon(String path) {
		BufferedImage image = null;

		try {
			image = ImageIO.read(Texture.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	public void loadTitle() {
		title = loadOtherIcon("/image/title.jpg");
	}

	public void loadFuelIcon() {
		fuel_icon = loadOtherIcon("/image/gas-pump.png");
	}

	public void loadMoneyIcon() {
		money_icon = loadOtherIcon("/image/dollar.png");
	}

	public void loadGoalIcon() {
		goal_icon = loadOtherIcon("/image/goalicon2.png");
	}

	public void loadIcon() {
		ore_icon = new BufferedImage[8];
		ore_icon[0] = loadOtherIcon("/ui/Image 139.png");
		ore_icon[1] = loadOtherIcon("/ui/Image 140.png");
		ore_icon[2] = loadOtherIcon("/ui/Image 141.png");
		ore_icon[3] = loadOtherIcon("/ui/Image 142.png");
		ore_icon[4] = loadOtherIcon("/ui/Image 138.png");
		ore_icon[5] = loadOtherIcon("/ui/Image 137.png");
		ore_icon[6] = loadOtherIcon("/ui/Image 136.png");
		ore_icon[7] = loadOtherIcon("/ui/Image 135.png");
	}

}
