package com.glitch.main.night;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class NightController {

    @GetMapping("/night")
    public String night(Model model) {
        model.addAttribute("name", "night");
        model.addAttribute("images", prepareImages());
        return "night";
    }

    /* To be replaced with a database eventually */
    private Image[] prepareImages() {
        return new Image[] {
                new Image(
                        "face.jpg",
                        "Face",
                        "To the right, a neon sign on an outside wall of a person with a heart on the cheek. To the left, the corner of a covered bar terrace where a group of friends are enjoying their night at a table."),
                new Image(
                        "canal.jpg",
                        "Canal",
                        "People sit by a well lit crowded bar. The background is completely black in comparison. A cyclists is going by, showing the red light on the back."),
                new Image(
                        "generator.jpg",
                        "Generator",
                        "Shot of the Generator Hostel. The building is adorned with vertical LED light strips of different colors. It looks nice at night."),
                new Image(
                        "red.jpg",
                        "Red",
                        "Bike lane along the side of a building that has red lights, making the entire lane bathed in red. One cyclist is riding away from the camera in the foreground. In the background, just by the light you can see another bike coming in the direction of the camera."),
                new Image(
                        "lidl.jpg",
                        "Lidl",
                        "Blue and yellow Lidl sign shining in the foreground on a dark boulevard."),
                new Image(
                        "building_20241111_200700.jpg",
                        "Building Colors",
                        "Narrow street ends with a tall apartment building with multiple colors of lights coming out of the windows."),
                new Image(
                        "statue_20241111_195736.jpg",
                        "Conversations and Olympians",
                        "Statue with blue colored woman athlete. Engraving: Les 4 Cardinales du Sports. Kuntu, \"ouest\" en quechua. Olympiade Culturelle 2024. Ville de Paris. Silhouettes of two men are chatting by the statue. Behind are lit up buildings along the Canal Saint Martin."),
                new Image(
                        "reflection_20241111_192418.jpg",
                        "Reflection",
                        "Lit up mid-rise building with it's window lights reflected in the still water it sits next to."),
                new Image(
                        "sandwich.jpg",
                        "Sandwich",
                        "Blue light from the windows of a building. There is a red neon sign that says \"SANDWICH\". Two people on the side are seen walking down the street next to it."),
                new Image(
                        "pompidou.jpg",
                        "Alone at the Pompidou",
                        "The Pompidou Centre at night. The building is lit up in many colors from the interior. The outside around it is empty except for two people sitting in the middle of the empty concrete together."),
                new Image(
                        "statue.jpg",
                        "Statue",
                        "The grey entrance of a building. There is a statue of a humanoid shape in place. Behind is what look like red, blue, and black shadows of human heads. There is a blue column and red column holding up the structure."),
                new Image(
                        "metro.jpg",
                        "Metro Entrance",
                        "A red Metro entrance sign post. Behind there are people heading towards the stairs and further behind are the lights of a busy commercial street. The metro lightpost gives off a white blueish light white the street lights are orange.")

        };
    }
}
