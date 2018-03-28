# RoverMapping

Run any of the following two commands to compile and run :

 - `./gradle run` will run the path finding algorithm on a 5x5 grid and display the initial, intermittent and final results on the Terminal.
 The map is initialzed empty, and the obstacles are enlisted in separately in the `map` file. In the map file, first two numbers are the index 
 in the 5x5 grid of the map, and 1 represents the value that will be set at that index -- in our map 1 represents obstacles.

 - `./gradle run -Pgui=true` will run the GUI version. This version allows testing the same algorithm on a larger map. 
Move the mouse on the white canvas to get the coordinate of that pixel. Each pixel represents 1cm^2 area.
In the text box, enter the start coordinate and end coordinate, press enter and left click on the white canvas to see the calculated path.
Make sure there is no leading white space when entering the coordinate. Check the Terminal for any cryptic error message
Click and hold the mouse from top-left to bottom-right to draw an obstacle. Right click on the canvas to reset the canvas. 
After new obstacles are drawn on the map, to see an updated path, focus on the textbox and press enter again - this will trigger
path recalculation. Finally, click on the white canvas to see the new path.
