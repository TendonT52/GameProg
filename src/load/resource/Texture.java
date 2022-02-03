package load.resource;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Texture {

	public BufferedImage[] array_block;

	public Texture() {
		create_allblock();
	}

	private BufferedImage splitImage(BufferedImage img, int col, int row, int width, int height) {
		return img.getSubimage(width * col, height * row, width, height);
	}

	public void create_allblock() {

		BufferedImage[] array_block = new BufferedImage[14];
		BufferedImage img = null;

		try {
			img = ImageIO.read(Texture.class.getResourceAsStream("/image/block.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			array_block[0] = ImageIO.read(Texture.class.getResourceAsStream("/image/50x50Invisible.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			array_block[13] = ImageIO.read(Texture.class.getResourceAsStream("/image/50x50Blue.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		array_block[1] = splitImage(img, 1, 0, 50, 50);
		array_block[2] = splitImage(img, 2, 0, 50, 50);
		array_block[3] = splitImage(img, 4, 0, 50, 50);
		array_block[4] = splitImage(img, 5, 0, 50, 50);
		array_block[5] = splitImage(img, 6, 0, 50, 50);
		array_block[6] = splitImage(img, 7, 0, 50, 50);
		array_block[7] = splitImage(img, 8, 0, 50, 50);
		array_block[8] = splitImage(img, 9, 0, 50, 50);
		array_block[9] = splitImage(img, 10, 0, 50, 50);
		array_block[10] = splitImage(img, 11, 0, 50, 50);
		array_block[11] = splitImage(img, 12, 0, 50, 50);
		array_block[12] = splitImage(img, 13, 0, 50, 50);
		this.array_block = array_block;
	}

}
