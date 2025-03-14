package club.imaginears.dashboard.chat;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * ComponentBuilder simplifies creating basic messages by allowing the use of a
 * chainable builder.
 * </p>
 * <pre>
 * new ComponentBuilder("Hello ").color(ChatColor.RED).
 * append("World").color(ChatColor.BLUE). append("!").bold(true).create();
 * </pre>
 * <p>
 * All methods (excluding {@link #append(String)} and {@link #create()} work on
 * the last part appended to the builder, so in the example above "Hello " would
 * be {@link ChatColor#RED} and "World" would be
 * {@link ChatColor#BLUE} but "!" would be bold and
 * {@link ChatColor#BLUE} because append copies the previous
 * part's formatting
 * </p>
 */
public final class ComponentBuilder {

    private BaseComponent current;
    private final List<BaseComponent> parts = new ArrayList<>();

    /**
     * Creates a ComponentBuilder from the other given ComponentBuilder to clone
     * it.
     *
     * @param original the original for the new ComponentBuilder.
     */
    public ComponentBuilder(ComponentBuilder original) {
        current = original.current.duplicate();
        for (BaseComponent baseComponent : original.parts) {
            parts.add(baseComponent.duplicate());
        }
    }

    /**
     * Creates a ComponentBuilder with the given text as the first part.
     *
     * @param text the first text element
     */
    public ComponentBuilder(String text) {
        current = new TextComponent(text);
    }

    /**
     * Creates a ComponentBuilder with the given component as the first part.
     *
     * @param component the first component element
     */
    public ComponentBuilder(BaseComponent component) {
        current = component.duplicate();
    }

    /**
     * Appends a component to the builder and makes it the current target for
     * formatting. The component will have all the formatting from previous
     * part.
     *
     * @param component the component to append
     * @return this ComponentBuilder for chaining
     */
    public ComponentBuilder append(BaseComponent component) {
        return append(component, FormatRetention.ALL);
    }

    /**
     * Appends a component to the builder and makes it the current target for
     * formatting. You can specify the amount of formatting retained from
     * previous part.
     *
     * @param component the component to append
     * @param retention the formatting to retain
     * @return this ComponentBuilder for chaining
     */
    public ComponentBuilder append(BaseComponent component, FormatRetention retention) {
        parts.add(current);

        BaseComponent previous = current;
        current = component.duplicate();
        current.copyFormatting(previous, retention, false);
        return this;
    }

    /**
     * Appends the components to the builder and makes the last element the
     * current target for formatting. The components will have all the
     * formatting from previous part.
     *
     * @param components the components to append
     * @return this ComponentBuilder for chaining
     */
    public ComponentBuilder append(BaseComponent[] components) {
        return append(components, FormatRetention.ALL);
    }

    /**
     * Appends the components to the builder and makes the last element the
     * current target for formatting. You can specify the amount of formatting
     * retained from previous part.
     *
     * @param components the components to append
     * @param retention  the formatting to retain
     * @return this ComponentBuilder for chaining
     */
    public ComponentBuilder append(BaseComponent[] components, FormatRetention retention) {
        Preconditions.checkArgument(components.length != 0, "No components to append");

        BaseComponent previous = current;
        for (BaseComponent component : components) {
            parts.add(current);

            current = component.duplicate();
            current.copyFormatting(previous, retention, false);
        }

        return this;
    }

    /**
     * Appends the text to the builder and makes it the current target for
     * formatting. The text will have all the formatting from previous part.
     *
     * @param text the text to append
     * @return this ComponentBuilder for chaining
     */
    public ComponentBuilder append(String text) {
        return append(text, FormatRetention.ALL);
    }

    /**
     * Parse text to BaseComponent[] with colors and format, appends the text to
     * the builder and makes it the current target for formatting. The component
     * will have all the formatting from previous part.
     *
     * @param text the text to append
     * @return this ComponentBuilder for chaining
     */
    public ComponentBuilder appendLegacy(String text) {
        return append(TextComponent.fromLegacyText(text));
    }

    /**
     * Appends the text to the builder and makes it the current target for
     * formatting. You can specify the amount of formatting retained from
     * previous part.
     *
     * @param text      the text to append
     * @param retention the formatting to retain
     * @return this ComponentBuilder for chaining
     */
    public ComponentBuilder append(String text, FormatRetention retention) {
        parts.add(current);

        BaseComponent old = current;
        current = new TextComponent(text);
        current.copyFormatting(old, retention, false);

        return this;
    }

    /**
     * Allows joining additional components to this builder using the given
     * {@link Joiner} and {@link FormatRetention#ALL}.
     * <p>
     * Simply executes the provided joiner on this instance to facilitate a
     * chain pattern.
     *
     * @param joiner joiner used for operation
     * @return this ComponentBuilder for chaining
     */
    public ComponentBuilder append(Joiner joiner) {
        return joiner.join(this, FormatRetention.ALL);
    }

    /**
     * Allows joining additional components to this builder using the given
     * {@link Joiner}.
     * <p>
     * Simply executes the provided joiner on this instance to facilitate a
     * chain pattern.
     *
     * @param joiner    joiner used for operation
     * @param retention the formatting to retain
     * @return this ComponentBuilder for chaining
     */
    public ComponentBuilder append(Joiner joiner, FormatRetention retention) {
        return joiner.join(this, retention);
    }

    /**
     * Sets the color of the current part.
     *
     * @param color the new color
     * @return this ComponentBuilder for chaining
     */
    public ComponentBuilder color(ChatColor color) {
        current.setColor(color);
        return this;
    }

    /**
     * Sets whether the current part is bold.
     *
     * @param bold whether this part is bold
     * @return this ComponentBuilder for chaining
     */
    public ComponentBuilder bold(boolean bold) {
        current.setBold(bold);
        return this;
    }

    /**
     * Sets whether the current part is italic.
     *
     * @param italic whether this part is italic
     * @return this ComponentBuilder for chaining
     */
    public ComponentBuilder italic(boolean italic) {
        current.setItalic(italic);
        return this;
    }

    /**
     * Sets whether the current part is underlined.
     *
     * @param underlined whether this part is underlined
     * @return this ComponentBuilder for chaining
     */
    public ComponentBuilder underlined(boolean underlined) {
        current.setUnderlined(underlined);
        return this;
    }

    /**
     * Sets whether the current part is strikethrough.
     *
     * @param strikethrough whether this part is strikethrough
     * @return this ComponentBuilder for chaining
     */
    public ComponentBuilder strikethrough(boolean strikethrough) {
        current.setStrikethrough(strikethrough);
        return this;
    }

    /**
     * Sets whether the current part is obfuscated.
     *
     * @param obfuscated whether this part is obfuscated
     * @return this ComponentBuilder for chaining
     */
    public ComponentBuilder obfuscated(boolean obfuscated) {
        current.setObfuscated(obfuscated);
        return this;
    }

    /**
     * Sets the insertion text for the current part.
     *
     * @param insertion the insertion text
     * @return this ComponentBuilder for chaining
     */
    public ComponentBuilder insertion(String insertion) {
        current.setInsertion(insertion);
        return this;
    }

    /**
     * Sets the click event for the current part.
     *
     * @param clickEvent the click event
     * @return this ComponentBuilder for chaining
     */
    public ComponentBuilder event(ClickEvent clickEvent) {
        current.setClickEvent(clickEvent);
        return this;
    }

    /**
     * Sets the hover event for the current part.
     *
     * @param hoverEvent the hover event
     * @return this ComponentBuilder for chaining
     */
    public ComponentBuilder event(HoverEvent hoverEvent) {
        current.setHoverEvent(hoverEvent);
        return this;
    }

    /**
     * Sets the current part back to normal settings. Only text is kept.
     *
     * @return this ComponentBuilder for chaining
     */
    public ComponentBuilder reset() {
        return retain(FormatRetention.NONE);
    }

    /**
     * Retains only the specified formatting. Text is not modified.
     *
     * @param retention the formatting to retain
     * @return this ComponentBuilder for chaining
     */
    public ComponentBuilder retain(FormatRetention retention) {
        current.retain(retention);
        return this;
    }

    /**
     * Returns the components needed to display the message created by this
     * builder.
     *
     * @return the created components
     */
    public BaseComponent[] create() {
        BaseComponent[] result = parts.toArray(new BaseComponent[parts.size() + 1]);
        result[parts.size()] = current;
        return result;
    }

    public enum FormatRetention {

        /**
         * Specify that we do not want to retain anything from the previous
         * component.
         */
        NONE,
        /**
         * Specify that we want the formatting retained from the previous
         * component.
         */
        FORMATTING,
        /**
         * Specify that we want the events retained from the previous component.
         */
        EVENTS,
        /**
         * Specify that we want to retain everything from the previous
         * component.
         */
        ALL
    }

    /**
     * Functional interface to join additional components to a ComponentBuilder.
     */
    public interface Joiner {

        /**
         * Joins additional components to the provided {@link ComponentBuilder}
         * and then returns it to fulfill a chain pattern.
         * <p>
         * Retention may be ignored and is to be understood as an optional
         * recommendation to the Joiner and not as a guarantee to have a
         * previous component in builder unmodified.
         *
         * @param componentBuilder to which to append additional components
         * @param retention        the formatting to possibly retain
         * @return input componentBuilder for chaining
         */
        ComponentBuilder join(ComponentBuilder componentBuilder, FormatRetention retention);
    }
}
