/*
 * Copyright (c) 2017.  Hanlin He
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

public class L733 {
    class Solution {
        public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
            if (image.length <= 0 || image[0].length <= 0)
                return image;
            if (sr < 0 || sc < 0 || sr >= image.length || sc >= image[0].length)
                return image;

            floodFill(image, sr, sc, newColor, image[sr][sc]);

            return image;
        }

        public void floodFill(int[][] image, final int sr, final int sc, final int newColor, final int oldColor) {
            if (sr < 0 || sc < 0 || sr >= image.length || sc >= image[0].length)
                return;
            if (image[sr][sc] == newColor || image[sr][sc] != oldColor)
                return;
            image[sr][sc] = newColor;
            floodFill(image, sr + 1, sc, newColor, oldColor);
            floodFill(image, sr - 1, sc, newColor, oldColor);
            floodFill(image, sr, sc + 1, newColor, oldColor);
            floodFill(image, sr, sc - 1, newColor, oldColor);
        }
    }
}
