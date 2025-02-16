from typing import List, Tuple

from flask import url_for


IMAGES = (
    {
        "filename": "face.jpg",
        "title": "Face",
        "alt": "To the right, a neon sign on an outside wall of a person with a heart on the cheek. To the left, the corner of a covered bar terrace where a group of friends are enjoying their night at a table.",
    },
    {
        "filename": "canal.jpg",
        "title": "Canal",
        "alt": "People sit by a well lit crowded bar. The background is completely black in comparison. A cyclists is going by, showing the red light on the back.",
    },
    {
        "filename": "generator.jpg",
        "title": "Generator",
        "alt": "Shot of the Generator Hostel. The building is adorned with vertical LED light strips of different colors. It looks nice at night.",
    },
    {
        "filename": "red.jpg",
        "title": "Red",
        "alt": "Bike lane along the side of a building that has red lights, making the entire lane bathed in red. One cyclist is riding away from the camera in the foreground. In the background, just by the light you can see another bike coming in the direction of the camera.",
    },
    {
        "filename": "lidl.jpg",
        "title": "Lidl",
        "alt": "Blue and yellow Lidl sign shining in the foreground on a dark boulevard.",
    },
    {
        "filename": "building_20241111_200700.jpg",
        "title": "Building Colors",
        "alt": "Narrow street ends with a tall apartment building with multiple colors of lights coming out of the windows.",
    },
    {
        "filename": "statue_20241111_195736.jpg",
        "title": "Conversations and Olympians",
        "alt": 'Statue with blue colored woman athlete. Engraving: Les 4 Cardinales du Sports. Kuntu, "ouest" en quechua. Olympiade Culturelle 2024. Ville de Paris. Silhouettes of two men are chatting by the statue. Behind are lit up buildings along the Canal Saint Martin.',
    },
    {
        "filename": "reflection_20241111_192418.jpg",
        "title": "Reflection",
        "alt": "Lit up mid-rise building with it's window lights reflected in the still water it sits next to.",
    },
    {
        "filename": "sandwich.jpg",
        "title": "Sandwich",
        "alt": 'Blue light from the windows of a building. There is a red neon sign that says "SANDWICH". Two people on the side are seen walking down the street next to it.',
    },
    {
        "filename": "pompidou.jpg",
        "title": "Alone at the Pompidou",
        "alt": "The Pompidou Centre at night. The building is lit up in many colors from the interior. The outside around it is empty except for two people sitting in the middle of the empty concrete together.",
    },
    {
        "filename": "statue.jpg",
        "title": "Statue",
        "alt": "The grey entrance of a building. There is a statue of a humanoid shape in place. Behind is what look like red, blue, and black shadows of human heads. There is a blue column and red column holding up the structure.",
    },
    {
        "filename": "metro.jpg",
        "title": "Metro Entrance",
        "alt": "A red Metro entrance sign post. Behind there are people heading towards the stairs and further behind are the lights of a busy commercial street. The metro lightpost gives off a white blueish light white the street lights are orange.",
    },
)


def generate_image_names() -> List[dict]:
    prepared_images = []

    for image in IMAGES:
        original_name = url_for(
            "static", filename=f"night/original/{image['filename']}"
        )
        thumbnail = url_for(
            "static", filename=f"night/thumbnail/prev_{image['filename']}"
        )

        prepared_images.append(
            {
                "original": original_name,
                "thumbnail": thumbnail,
                "title": image["title"],
                "alt": image["alt"],
            }
        )

    return prepared_images
