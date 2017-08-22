package ctc.kopo.pchu.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Polytech on 2017-08-01.
 */

public final class ColorItems {

    /**
     * A key for saving the {@link ColorItem}s of the user.
     */
    private static final String KEY_SAVED_COLOR_ITEMS = "Colors.Keys.SAVED_COLOR_ITEMS";

    /**
     * A key for saving the last picked color.
     */
    private static final String KEY_LAST_PICKED_COLOR = "Colors.Keys.LAST_PICKED_COLOR";

    /**
     * The default last picked color.
     */
    private static final int DEFAULT_LAST_PICKED_COLOR = Color.WHITE;

    /**
     * A {@link com.google.gson.Gson} for serializing/deserializing objects.
     */
    private static final Gson GSON = new Gson();

    /**
     * A {@link java.lang.reflect.Type} instance of a {@link java.util.List} of {@link ColorItem}s.
     */
    private static final Type COLOR_ITEM_LIST_TYPE = new TypeToken<List<ColorItem>>() {
    }.getType();

    /**
     * A {@link java.util.Comparator} for sorting {@link ColorItem}s in chronological order of creation.
     */
    public static final Comparator<ColorItem> CHRONOLOGICAL_COMPARATOR = new Comparator<ColorItem>() {
        @Override
        public int compare(ColorItem lhs, ColorItem rhs) {
            return (int) (rhs.getId() - lhs.getId());
        }
    };

    /**
     * Get the {@link android.content.SharedPreferences} used for saving/restoring data.
     *
     * @param context a {@link android.content.Context}.
     * @return the {@link android.content.SharedPreferences} used for saving/restoring data.
     */
    private static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Get the last picked color.
     *
     * @param context a {@link android.content.Context}.
     * @return the last picked color.
     */
    public static int getLastPickedColor(Context context) {
        return getPreferences(context).getInt(KEY_LAST_PICKED_COLOR, DEFAULT_LAST_PICKED_COLOR);
    }

    /**
     * Save the last picked color.
     *
     * @param context         a {@link android.content.Context}.
     * @param lastPickedColor the last picked color.
     * @return Returns true if the new color was successfully written to persistent storage.
     */
    public static boolean saveLastPickedColor(Context context, int lastPickedColor) {
        final SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putInt(KEY_LAST_PICKED_COLOR, lastPickedColor);
        return editor.commit();
    }

    /**
     * Get the {@link ColorItem}s of the user.
     *
     * @param context a {@link android.content.Context}.
     * @return a {@link java.util.List} of {@link ColorItem}s.
     */
    public static List<ColorItem> getSavedColorItems(Context context) {
        return getSavedColorItems(getPreferences(context));
    }

    /**
     * Get the {@link ColorItem}s of the user.
     *
     * @param sharedPreferences a {@link android.content.SharedPreferences} from which the {@link ColorItem}s will be retrieved. // JSON 파싱하는 곳
     * @return a {@link java.util.List} of {@link ColorItem}s.
     */
    @SuppressWarnings("unchecked")
    public static List<ColorItem> getSavedColorItems(SharedPreferences sharedPreferences) {
        final String jsonColorItems = sharedPreferences.getString(KEY_SAVED_COLOR_ITEMS, "");

        // No saved colors were found.
        // Return an empty list.
        if ("".equals(jsonColorItems)) {
            return new ArrayList<>();
        }

        // Parse the json into colorItems.
        final List<ColorItem> colorItems = GSON.fromJson(jsonColorItems, COLOR_ITEM_LIST_TYPE);

        // Sort the color items chronologically.
        Collections.sort(colorItems, CHRONOLOGICAL_COMPARATOR);
/*        final List<ColorItem> colorItems1 = new ArrayList<ColorItem>();

        if(colorItems.size() < 5){
            colorItems1.add(colorItems.get(0));
            colorItems1.add(colorItems.get(0));
            colorItems1.add(colorItems.get(0));
            colorItems1.add(colorItems.get(0));
            colorItems1.add(colorItems.get(0));
        }else{
            for (int i = 0; i < 5; i++) {   // colorItems.size = 2이면 두번 for문 돌텐데..
                colorItems1.add(colorItems.get(i));    // 그럼 colorItems1.add에서는 colorItems.get(0)과 colorItems.get(1) 이 들어감..
            }
        }*/
        /*
        if(colorItems.size() < 5){
            for(int j=0; j<(5-colorItems.size()); j++){
                colorItems1.add(colorItems.get(0));
            }
        }*/
        return colorItems;
    }

    /**
     * Save a {@link ColorItem}s.
     *
     * @param context     a {@link android.content.Context}.
     * @param colorToSave the {@link ColorItem} to save.
     * @return Returns true if the new {@link ColorItem} was successfully written to persistent storage.
     */

    //ColorItems.saveColorItem(this, new ColorItem(mLastPickedColor));
    public static boolean saveColorItem(Context context, ColorItem colorToSave) {
        if (colorToSave == null) {
            throw new IllegalArgumentException("Can't save a null color.");
        }

        final List<ColorItem> savedColorsItems = getSavedColorItems(context);
        final SharedPreferences.Editor editor = getPreferences(context).edit();
        final List<ColorItem> colorItems = new ArrayList<>(savedColorsItems.size() + 1);

        // Add the saved color items except the one with the same ID. It will be overridden.
        final int size = savedColorsItems.size();
        for (int i = 0; i < size; i++) {
            final ColorItem candidate = savedColorsItems.get(i);
            if (candidate.getId() != colorToSave.getId()) {
                colorItems.add(candidate);
            }
        }

        // Add the new color to save
        colorItems.add(colorToSave);

        editor.putString(KEY_SAVED_COLOR_ITEMS, GSON.toJson(colorItems));
        return editor.commit();
    }


    /**
     * Delete a {@link ColorItem}.
     *
     * @param context           a {@link android.content.Context}.
     * @param colorItemToDelete the {@link ColorItem} to be deleted.
     * @return Returns true if the color was successfully deleted from persistent storage.
     */
    public static boolean deleteColorItem(Context context, ColorItem colorItemToDelete) {
        if (colorItemToDelete == null) {
            throw new IllegalArgumentException("Can't delete a null color item");
        }

        final SharedPreferences sharedPreferences = getPreferences(context);
        final List<ColorItem> savedColorsItems = getSavedColorItems(sharedPreferences);

        for (Iterator<ColorItem> it = savedColorsItems.iterator(); it.hasNext(); ) {
            final ColorItem candidate = it.next();
            if (candidate.getId() == colorItemToDelete.getId()) {
                it.remove();
                final SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_SAVED_COLOR_ITEMS, GSON.toJson(savedColorsItems));
                return editor.commit();
            }
        }

        return false;
    }

    /**
     * Register a {@link ColorItems.OnColorItemChangeListener}.
     * <p/>
     * <b>Caution</b>: No strong references to the listener are currently stored. You must store a strong reference to the listener, or it will be susceptible to garbage collection.
     *
     * @param context                   a {@link android.content.Context}.
     * @param onColorItemChangeListener the {@link ColorItems.OnColorItemChangeListener} to register.
     */
    public static void registerListener(Context context, OnColorItemChangeListener onColorItemChangeListener) {
        getPreferences(context).registerOnSharedPreferenceChangeListener(onColorItemChangeListener);
    }

    /**
     * Unregister a {@link ColorItems.OnColorItemChangeListener}.
     *
     * @param context                   a {@link android.content.Context}.
     * @param onColorItemChangeListener the {@link ColorItems.OnColorItemChangeListener} to unregister.
     */
    public static void unregisterListener(Context context, OnColorItemChangeListener onColorItemChangeListener) {
        getPreferences(context).unregisterOnSharedPreferenceChangeListener(onColorItemChangeListener);
    }

    /**
     * A simple class for listening to the changes of the {@link ColorItem}s of the user.
     */
    public abstract static class OnColorItemChangeListener implements SharedPreferences.OnSharedPreferenceChangeListener {

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (KEY_SAVED_COLOR_ITEMS.equals(key)) {
                onColorItemChanged(getSavedColorItems(sharedPreferences));
            }
        }

        /**
         * Called when the {@link ColorItem}s of the user have just changed.
         *
         * @param colorItems the current {@link java.util.List} of the {@link ColorItem}s of the user.
         */
        public abstract void onColorItemChanged(List<ColorItem> colorItems);
    }

    // Non-instantiability
    private ColorItems() {
    }
}
