package load.resource;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageObject {
	private BufferedImage image;

	public ImageObject(String path) {
		this.image = create_image(path);
	}

	public BufferedImage create_image(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(Texture.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

}
