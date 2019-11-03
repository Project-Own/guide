package com.example.guide.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guide.Modal.Places;
import com.example.guide.NavigationBar;
import com.example.guide.R;
import com.example.guide.adapters.PlacesAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {


    Activity activity;
    Context context;
    private RecyclerView recyclerView;
    private List<Places> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        context = this;
        activity = FoodActivity.this;


        DrawerLayout drawer = findViewById(R.id.drawer_layout1);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationBar(context, drawer, this.getClass().getSimpleName()));

        recyclerView = findViewById(R.id.places_recyclerView);
        foodList = new ArrayList<>();
        foodList.add(new Places("King Curd", "If there is something better than the best then it is King curd(Ju Ju Dhau) of Bhaktapur." +
                "This curd is made through an age old method without using sophisticated tools. Due to its uniqueness in taste, its " +
                "one of its kind and is must try item when you visit to Bhaktapur.It is the speciality of  town of Bhaktapur. Juju dhau means the “King Yogurt”. " +
                "Juju Dhau is rich in taste with thick creamy consistency. A must try for food lovers.\n" +
                "\n ", "kingcurd"));

        foodList.add(new Places("Choila", "  Choyela is a typical Newari food that consists of spiced grilled water buffalo meat. Though choyela " +
                "is traditionally popular with water buffalo meat, nowadays mutton, chicken and duck meat are also being used.  It is one of " +
                "the most important and necessary item in the Newari Menu. It is also an important ingredient of Samay Baji. " +
                "It is practically eaten daily if one can afford it and in almost any context mostly at feasts. Usually eaten with beaten rice, choyela is typically " +
                "very spicy & mouth watering. There are two variety of choyela: Haku choyela and Mana choyela. Haku choyela is" +
                " prepared by roasting the meat directly on the coals of the hearth or by frying meat in pan with oil. Mana choyala " +
                "is made by first boiling the meat, then spices are added, and lastly hot oil is added into it. ", "choila"));

        foodList.add(new Places("Chatamari", "\n" +
                "Chatamari is one of the most popular newari food. It is a kind of very thin pancake or crepe usually made from rice flour. It can" +
                " be topped with variety of topping specially minced red meat and egg or vegetables and seasoned with vegetables like tomatoes,onions" +
                ",green chilies etc. Chatamari is eaten as a snack or an appetizer during newari festivals and other special occasions. It is a compulsory " +
                "dish of Newar in festive occasions like Guthi, Dewali or Degudyopuja. Nowadays, Chatamari is famous as an appetizer in small Newari entries to large restaurants.\n ", "chatamari"));

        foodList.add(new Places("Bara", " Woh is delicious lentil spongy pancake or patty and a very popular newari food. It is " +
                "prepared by grinded lentils which are soaked for overnight. It can make from any type of lentil like black lentil, green lentil or " +
                "any other type. Batter for woh is made by mixing grinded lentils, salt, cumin powder and ginger paste which is fried in flat pan putting" +
                " little oil. It can be topped with minced meat(buff, chicken or other),egg, vegetables etc like in chatamari." +
                "t is generally eaten on special occasion called Sithi Nakha and other religious Newari festivals by Newars. But it is also popular as street" +
                " food or appetizers among other people in Nepal. It is used with other food. It is used as side dish in ‘Samye Baji’.\n", "bara"));

        foodList.add(new Places("Kachila", "Kachilla is a popular Newari food that uses raw minced meat and whole lot of spices." +
                " The word “kachilla” itself translates to “kacho masu” or “raw meat.” The raw meat is mixed with spices and oil with hands, not " +
                "a spatula, to generate enough heat to make the meat tender as well as to get rid of the raw fragrance." +
                "Kachila is generally eaten as snack at joyous occasions or small intimate feasts and not at large feasts in Newar Community.","kachila"));

        foodList.add(new Places("Kwati", "Kwati is a mixed soup of nine types of sprouted beans. It is a traditional Newari dish " +
                "consumed on the festival of Gun Punhi.Kwāti is eaten as a delicacy and for its health benefits and ritual significance." +
                "Kwati is known to be a healthy food. They say that it cures cold, cough and is one of the best foods for women in their maternity leave.","kwati"));

        foodList.add(new Places("Bhuttan", "Bhutan is made out of intestines, liver and stomach of a goat. Goat meat Bhutan" +
                " is a traditional Newari food that is eaten in Nepal especially in festival seasons. It is mostly eaten with beaten rice and " +
                "goes perfectly as a side dish, appetiser with liquor drinks.","bhuttan"));

        foodList.add(new Places("Yomari", " Yomari is the special dish of Newars. It is the Newari sweet dish that " +
                "is best served hot. Yomari is prepared by filling the dumpling of rice flour which is shaped in the form of fig with " +
                "mixture of molasses and sesame seeds. The filling mixture can be substituted by lentil paste or milk solids called Khuwa.\n" +
                "Yomari was traditionally prepared especially in Yomari Punhi, but these days, it is available in all Newari hotels and " +
                "restaurants in Kathmandu. It is also now popular among people other than Newars of Newar community. ", "yomori"));

        foodList.add(new Places("MoMo cha", "  Momo is a type of dumpling which is made from dough usually filled with " +
                "minced buffalo meat. Although it is very popular among Newars as well as other Nepalese all over world, it is not original " +
                "newari food. It is adapted from Lasha(Tibet) by travelling Newar Merchants centuries ago and modify with available " +
                "ingredients in their own newari way.Momo is mainly eaten as appetizer. Momo is usually steamed with different filling " +
                "such as buff, chicken, mutton, pork and vegetable. Among these variety Buff momo (Momo with minced buffalo meat) is the" +
                " most popular momo in Nepal. Nowadays different types of momos are also available such as C-Momo, Kothe Momo, Fried Momo " +
                "etc. It is served with spicy achar(especially jhol achar made up of cumin, soyabean and tomato) and eaten hot.\n"
                , "momocha"));

        foodList.add(new Places("Smaey Baji", " Samay Baji is an authentic traditional Newari food of Newar Community." +
                " Samya baji is one of the main attractions of Nepal as well. It is taken as appetizer in the restaurants nowadays. " +
                "Samay Baji is taken as starter during the major festivals of Nepal like Indra Jatra(Yenya Punhi), Dashain(Mohani), " +
                "Tihar(Sunti) as well as almost every newari festivals, family get-together and religious activities of newars." +
                "Samay Baji is an authentic traditional Newari food of Newar Community. Samya baji is one of the main attractions" +
                " of Nepal as well. It is taken as appetizer in the restaurants nowadays. Samay Baji is taken as starter during the" +
                " major festivals of Nepal like Indra Jatra(Yenya Punhi), Dashain(Mohani), Tihar(Sunti) as well as almost every newari " +
                "festivals, family get-together and religious activities of newars.", "samey"));

        foodList.add(new Places("Gwaramari (Bread Loaf)\n" , "Gwaramari literally means \"a round loaf of bread\". Gwaramari consists " +
                "of two words, Gwara and Mari. “Gwara” means round shaped and “Mari” means bread. It is very easy and interesting newari" +
                " food recipe (sub category of Nepalese Cuisine). Gwaramari is generally eaten as breakfast in Newar Community." +
                "Gwaramari is made up of flour, baking powder (soda) and salt. Taking small round portion of thick paste made by mixture " +
                "of these three things and water is fried in hot oil in flat bottomed pan until it turns golden brown. It is very tasty and " +
                "enjoyable especially when it is hot.\n", "gwara"));






        PlacesAdapter adapter = new PlacesAdapter(foodList, recyclerView, context, activity);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout1);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
