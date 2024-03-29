package com.aluxian.drizzle.multi.adapters;

import com.aluxian.drizzle.multi.MultiTypeAdapter;
import com.aluxian.drizzle.multi.items.MultiTypeBaseItem;
import com.aluxian.drizzle.multi.items.MultiTypeStyleableItem;
import com.aluxian.drizzle.multi.traits.MultiTypeHeader;
import com.aluxian.drizzle.utils.Log;
import com.aluxian.drizzle.utils.UberSwatch;

/**
 * A {@link com.aluxian.drizzle.multi.MultiTypeAdapter} that supports {@link com.aluxian.drizzle.multi.items.MultiTypeStyleableItem}s.
 */
public abstract class MultiTypeStyleableAdapter extends MultiTypeAdapter {

    /** The currently set colors. */
    protected UberSwatch mSwatch;

    /**
     * Set the colour palette that the items should use.
     *
     * @param swatch The colour palette to use.
     */
    public void setStyle(UberSwatch swatch) {
        mSwatch = swatch;

        // Update existing items
        for (int i = 0; i < itemsList().size(); i++) {
            MultiTypeBaseItem<? extends MultiTypeBaseItem.ViewHolder> item = itemsList().get(i);

            if (item instanceof MultiTypeStyleableItem && !(item instanceof MultiTypeHeader)) {
                notifyItemChanged(i);
            }
        }
    }

    @Override
    public void onBindViewHolder(MultiTypeBaseItem.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        MultiTypeBaseItem<? extends MultiTypeBaseItem.ViewHolder> item = itemsList().get(position);

        // Set the colour palette
        if (mSwatch != null && item instanceof MultiTypeStyleableItem) {
            MultiTypeStyleableItem styleableItem = (MultiTypeStyleableItem) item;
            styleableItem.setStyle(holder, mSwatch);
            Log.d("changing style on item " + position);
        }
    }

}
