package com.example.guide.ui.food;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.guide.Model.Places;

import java.util.ArrayList;
import java.util.List;

public class FoodViewModel extends AndroidViewModel {


    private MutableLiveData<List<Places>> foodList;

    public FoodViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Places>> loadFood() {
        if (foodList == null) {
            foodList = new MutableLiveData<>();
            loadFoodsList();
        }
        return foodList;
    }

    private void loadFoodsList() {
        List<Places> food = new ArrayList<>();
        food.add(new Places("King Curd", "If there is something better than the best then it is King curd(Ju Ju Dhau) of Bhaktapur." +
                "This curd is made through an age old method without using sophisticated tools. Due to its uniqueness in taste, its " +
                "one of its kind and is must try item when you visit to Bhaktapur.It is the speciality of  town of Bhaktapur. Juju dhau means the “King Yogurt”. " +
                "Juju Dhau is rich in taste with thick creamy consistency. A must try for food lovers.\n" +
                "\n ", "kingcurd",27.671622, 85.428231));

        food.add(new Places("Choila", "  Choyela is a typical Newari food that consists of spiced grilled water buffalo meat. Though choyela " +
                "is traditionally popular with water buffalo meat, nowadays mutton, chicken and duck meat are also being used.  It is one of " +
                "the most important and necessary item in the Newari Menu. It is also an important ingredient of Samay Baji. " +
                "It is practically eaten daily if one can afford it and in almost any context mostly at feasts. Usually eaten with beaten rice, choyela is typically " +
                "very spicy & mouth watering. There are two variety of choyela: Haku choyela and Mana choyela. Haku choyela is" +
                " prepared by roasting the meat directly on the coals of the hearth or by frying meat in pan with oil. Mana choyala " +
                "is made by first boiling the meat, then spices are added, and lastly hot oil is added into it. ", "choila",27.672063, 85.428112));

        food.add(new Places("Chatamari", "\n" +
                "Chatamari is one of the most popular newari food. It is a kind of very thin pancake or crepe usually made from rice flour. It can" +
                " be topped with variety of topping specially minced red meat and egg or vegetables and seasoned with vegetables like tomatoes,onions" +
                ",green chilies etc. Chatamari is eaten as a snack or an appetizer during newari festivals and other special occasions. It is a compulsory " +
                "dish of Newar in festive occasions like Guthi, Dewali or Degudyopuja. Nowadays, Chatamari is famous as an appetizer in small Newari entries to large restaurants.\n ", "chatamari",27.672063, 85.428112));

        food.add(new Places("Bara", " Woh is delicious lentil spongy pancake or patty and a very popular newari food. It is " +
                "prepared by grinded lentils which are soaked for overnight. It can make from any type of lentil like black lentil, green lentil or " +
                "any other type. Batter for woh is made by mixing grinded lentils, salt, cumin powder and ginger paste which is fried in flat pan putting" +
                " little oil. It can be topped with minced meat(buff, chicken or other),egg, vegetables etc like in chatamari." +
                "t is generally eaten on special occasion called Sithi Nakha and other religious Newari festivals by Newars. But it is also popular as street" +
                " food or appetizers among other people in Nepal. It is used with other food. It is used as side dish in ‘Samye Baji’.\n", "bara",27.672063, 85.428112));

        food.add(new Places("Kachila", "Kachilla is a popular Newari food that uses raw minced meat and whole lot of spices." +
                " The word “kachilla” itself translates to “kacho masu” or “raw meat.” The raw meat is mixed with spices and oil with hands, not " +
                "a spatula, to generate enough heat to make the meat tender as well as to get rid of the raw fragrance." +
                "Kachila is generally eaten as snack at joyous occasions or small intimate feasts and not at large feasts in Newar Community.", "kachila",27.672063, 85.428112));

        food.add(new Places("Kwati", "Kwati is a mixed soup of nine types of sprouted beans. It is a traditional Newari dish " +
                "consumed on the festival of Gun Punhi.Kwāti is eaten as a delicacy and for its health benefits and ritual significance." +
                "Kwati is known to be a healthy food. They say that it cures cold, cough and is one of the best foods for women in their maternity leave.", "kwati",27.672063, 85.428112));

        food.add(new Places("Bhuttan", "Bhuttan is made out of intestines, liver and stomach of a goat. Goat meat Bhutan" +
                " is a traditional Newari food that is eaten in Nepal especially in festival seasons. It is mostly eaten with beaten rice and " +
                "goes perfectly as a side dish, appetiser with liquor drinks.", "bhuttan",27.672063, 85.428112));

        food.add(new Places("Yomari", " Yomari is the special dish of Newars. It is the Newari sweet dish that " +
                "is best served hot. Yomari is prepared by filling the dumpling of rice flour which is shaped in the form of fig with " +
                "mixture of molasses and sesame seeds. The filling mixture can be substituted by lentil paste or milk solids called Khuwa.\n" +
                "Yomari was traditionally prepared especially in Yomari Punhi, but these days, it is available in all Newari hotels and " +
                "restaurants in Kathmandu. It is also now popular among people other than Newars of Newar community. ", "yomori",27.672063, 85.428112));

        food.add(new Places("MoMo cha", "  Momo is a type of dumpling which is made from dough usually filled with " +
                "minced buffalo meat. Although it is very popular among Newars as well as other Nepalese all over world, it is not original " +
                "newari food. It is adapted from Lasha(Tibet) by travelling Newar Merchants centuries ago and modify with available " +
                "ingredients in their own newari way.Momo is mainly eaten as appetizer. Momo is usually steamed with different filling " +
                "such as buff, chicken, mutton, pork and vegetable. Among these variety Buff momo (Momo with minced buffalo meat) is the" +
                " most popular momo in Nepal. Nowadays different types of momos are also available such as C-Momo, Kothe Momo, Fried Momo " +
                "etc. It is served with spicy achar(especially jhol achar made up of cumin, soyabean and tomato) and eaten hot.\n"
                , "momocha",27.672063, 85.428112));

        food.add(new Places("Smaey Baji", " Samay Baji is an authentic traditional Newari food of Newar Community." +
                " Samya baji is one of the main attractions of Nepal as well. It is taken as appetizer in the restaurants nowadays. " +
                "Samay Baji is taken as starter during the major festivals of Nepal like Indra Jatra(Yenya Punhi), Dashain(Mohani), " +
                "Tihar(Sunti) as well as almost every newari festivals, family get-together and religious activities of newars." +
                "Samay Baji is an authentic traditional Newari food of Newar Community. Samya baji is one of the main attractions" +
                " of Nepal as well. It is taken as appetizer in the restaurants nowadays. Samay Baji is taken as starter during the" +
                " major festivals of Nepal like Indra Jatra(Yenya Punhi), Dashain(Mohani), Tihar(Sunti) as well as almost every newari " +
                "festivals, family get-together and religious activities of newars.", "samey",27.672063, 85.428112));

        food.add(new Places("Gwaramari (Bread Loaf)\n", "Gwaramari literally means \"a round loaf of bread\". Gwaramari consists " +
                "of two words, Gwara and Mari. “Gwara” means round shaped and “Mari” means bread. It is very easy and interesting newari" +
                " food recipe (sub category of Nepalese Cuisine). Gwaramari is generally eaten as breakfast in Newar Community." +
                "Gwaramari is made up of flour, baking powder (soda) and salt. Taking small round portion of thick paste made by mixture " +
                "of these three things and water is fried in hot oil in flat bottomed pan until it turns golden brown. It is very tasty and " +
                "enjoyable especially when it is hot.\n", "gwara",27.672063, 85.428112));

        foodList.setValue(food);

    }

}