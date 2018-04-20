![](https://github.com/SDSU-Robotics/RoverMapping/blob/master/screenshot.jpg)


# RoverMapping

Run any of the following two commands to compile and run :

 - `./gradlew run` will run the path finding algorithm on a 5x5 grid and display the initial, intermittent and final results on the Terminal.
 The map is initialzed empty, and the obstacles are enlisted in separately in the `map` file. In the map file, first two numbers are the index 
 in the 5x5 grid of the map, and 1 represents the value that will be set at that index -- in our map 1 represents obstacles.

 - `./gradlew run -Pgui=true` will run the GUI version. This version allows testing the same algorithm on a larger map. 
Move the mouse on the white canvas to get the coordinate of that pixel. The white canvas reprsents a 600 cm^2 area. Each pixel represents 1cm^2 area. Each small squares represents 10cm^2 area. Each black squares represnts 60cm^2 area. In the top right text box, enter the start coordinate and end coordinate, press enter and to see the calculated path. Make sure there is no leading white space when entering the coordinates. Click and drag to draw obstacles on the map. Drawn maps can saved and loaded using the save and load button. Make sure to enter a file name first.

