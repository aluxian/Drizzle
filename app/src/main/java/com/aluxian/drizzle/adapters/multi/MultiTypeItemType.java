package com.aluxian.drizzle.adapters.multi;

import java.util.Objects;

/**
 * Holds data about an item type used in a MultiTypeAdapter.
 *
 * @param <VH> The item's ViewHolder.
 */
public class MultiTypeItemType<VH extends MultiTypeBaseItem.ViewHolder> {

    public final Class itemClass;
    public final Class<VH> viewHolderClass;
    public final int layoutId;

    public MultiTypeItemType(Class itemClass, Class<VH> viewHolderClass, int layoutId) {
        this.itemClass = itemClass;
        this.viewHolderClass = viewHolderClass;
        this.layoutId = layoutId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MultiTypeItemType itemType = (MultiTypeItemType) o;

        return Objects.equals(itemClass, itemType.itemClass)
                && Objects.equals(viewHolderClass, itemType.viewHolderClass)
                && layoutId == itemType.layoutId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemClass, viewHolderClass, layoutId);
    }

}
