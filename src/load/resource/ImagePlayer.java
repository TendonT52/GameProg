package load.resource;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImagePlayer {

	public BufferedImage[] player_animation_up;
	public BufferedImage[] player_animation_down;
	public BufferedImage[] player_animation_left;
	public BufferedImage[] player_animation_right;

	public ImagePlayer() {
		create_player_animation_up();
		create_player_animation_down();
		create_player_animation_left();
		create_player_animation_right();
	}

	private BufferedImage splitImage(BufferedImage img, int col, int row, int width, int height) {
		return img.getSubimage(width * col, height * row, width, height);
	}

	private BufferedImage[] createImagePlayer(String path, int number) {
		BufferedImage[] player_animation = new BufferedImage[4];
		BufferedImage image = null;
		try {
			image = ImageIO.read(Texture.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		player_animation[0] = splitImage(image, 0, number, 53, 53);
		player_animation[1] = splitImage(image, 1, number, 53, 53);
		player_animation[2] = splitImage(image, 2, number, 53, 53);
		player_animation[3] = splitImage(image, 3, number, 53, 53);
		return player_animation;
	}

	public void create_player_animation_up() {
		this.player_animation_up = createImagePlayer("/image/player.png", 1);
	}

	public void create_player_animation_down() {
		this.player_animation_down = createImagePlayer("/image/player.png", 0);
	}

	public void create_player_animation_left() {
		this.player_animation_left = createImagePlayer("/image/player.png", 3);
	}

	public void create_player_animation_right() {
		this.player_animation_right = createImagePlayer("/image/player.png", 2);
	}

}
