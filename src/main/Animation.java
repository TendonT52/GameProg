package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Animation {
	private int index = 0;
	private int count;

	private BufferedImage[] animationFrame;
	private BufferedImage currImage;

	public Animation(BufferedImage[] animationFrame) {
		count = 0;
		this.animationFrame = animationFrame;
		currImage = animationFrame[0];
	}

	public void runAnimation(int speed) {
		index += speed * speed;
		if (index > (GamePanel.fps - speed)) {
			index = 0;
			create_nextFrame();
		}
	}

	private void create_nextFrame() {
		count++;
		if (count >= animationFrame.length) {
			count = 0;
		}
		currImage = animationFrame[count];
	}

	public void drawAnimation(Graphics2D graphics2d, int x, int y) {
		graphics2d.drawImage(currImage, x, y, null);
		runAnimation(4);
	}

	public BufferedImage getCurrentFrame() {
		return currImage;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public BufferedImage[] getAnimationFrame() {
		return animationFrame;
	}

	public void setAnimationFrame(BufferedImage[] animationFrame) {
		this.animationFrame = animationFrame;
	}

	public void setCurrImage(BufferedImage currImage) {
		this.currImage = currImage;
	}

}
