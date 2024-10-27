package com.goby56.wakes.config.gui;

import com.goby56.wakes.config.WakesConfigScreen;
import com.goby56.wakes.render.enums.WakeColor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.render.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ColorPicker extends ClickableWidget {
    private static final Identifier FRAME_TEXTURE = Identifier.ofVanilla("widget/slot_frame");

    private final Map<String, Bounded> widgets = new HashMap<>();
    private final AABB bounds;
    private PickListener listener;

    public interface PickListener {
        void onPickedColor(WakeColor color);
    }


    public ColorPicker(WakesConfigScreen screenContext, int x, int y, int width, int height) {
        super(x, y, width, height, Text.of(""));

        this.bounds = new AABB(0, 0, 1f, 2f / 3f, x, y, width, height);

        this.widgets.put("hexInputField", new HexInputField(new AABB(0f, 5f / 6f, 5f / 12f, 1f, x, y, width, height), screenContext.textRenderer));
        this.widgets.put("hueSlider", new GradientSlider(new AABB(0f, 2f / 3f, 1f, 5f / 6f, x, y, width, height)));
        this.widgets.put("gradientSlider", new GradientSlider(new AABB(5f / 12f, 5f / 6f, 1f, 1f, x, y, width, height)));

        screenContext.addWidget(this);
        for (var widget : this.widgets.values()) {
            screenContext.addWidget(widget.getWidget());
        }
    }

    public void toggleActive() {
        boolean b = !this.active;
        this.active = this.visible = b;
        System.out.println(b);
        for (var widget : this.widgets.values()) {
            widget.getWidget().active = widget.getWidget().visible = b;
        }
    }

    public void registerListener(PickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        for (var widget : this.widgets.values()) {
            if (widget.getBounds().contains((int) mouseX, (int) mouseY)) {
                widget.getWidget().onClick(mouseX, mouseY);
                return;
            }
        }
        super.onClick(mouseX, mouseY);
    }

    @Override
    public void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
        for (var widget : this.widgets.values()) {
            if (widget.getBounds().contains((int) mouseX, (int) mouseY)) {
                widget.getWidget().onDrag(mouseX, mouseY, deltaX, deltaY);
                return;
            }
        }
        super.onDrag(mouseX, mouseY, deltaX, deltaY);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        if (!active) return;

        int x = bounds.x;
        int y = bounds.y;
        int w = bounds.width;
        int h = bounds.height;

        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        Matrix4f matrix = context.getMatrices().peek().getPositionMatrix();
        buffer.vertex(matrix, x, y, 5).color(Color.HSBtoRGB(1, 0, 1));
        buffer.vertex(matrix, x, y + h, 5).color(Color.HSBtoRGB(1, 0, 0));
        buffer.vertex(matrix, x + w, y + h, 5).color(Color.HSBtoRGB(1, 1, 0));
        buffer.vertex(matrix, x + w, y, 5).color(Color.HSBtoRGB(1, 1, 1));
        BufferRenderer.drawWithGlobalProgram(buffer.end());
        context.drawGuiTexture(FRAME_TEXTURE, x, y, w, h);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

    public interface Bounded {
        AABB getBounds();
        ClickableWidget getWidget();
    }

    public static class AABB {
        public int x;
        public int y;
        public int width;
        public int height;

        public AABB(float fracX1, float fracY1, float fracX2, float fracY2, int globX, int globY, int totWidth, int totHeight) {
            this.x = (int) (fracX1 * totWidth) + globX;
            this.y = (int) (fracY1 * totHeight) + globY;
            this.width = (int) ((fracX2 - fracX1) * totWidth);
            this.height = (int) ((fracY2 - fracY1) * totHeight);
        }

        public boolean contains(int x, int y) {
            return this.x <= x && x < this.x + this.width &&
                    this.y <= y && y < this.y + this.height;
        }
    }

    private static class HexInputField extends TextFieldWidget implements Bounded {
        protected AABB bounds;

        public HexInputField(AABB bounds, TextRenderer textRenderer) {
            super(textRenderer, bounds.x, bounds.y, bounds.width, bounds.height, Text.of("HEX"));
            this.bounds = bounds;
        }

        @Override
        public AABB getBounds() {
            return this.bounds;
        }

        @Override
        public ClickableWidget getWidget() {
            return this;
        }
    }

    private static class GradientSlider extends SliderWidget implements Bounded {
        protected AABB bounds;

        public GradientSlider(AABB bounds) {
            super(bounds.x, bounds.y, bounds.width, bounds.height, Text.of(""), 1f);
            this.bounds = bounds;
        }

        @Override
        public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
            super.renderWidget(context, mouseX, mouseY, delta);
        }

        @Override
        protected void updateMessage() {

        }

        @Override
        protected void applyValue() {

        }

        @Override
        public AABB getBounds() {
            return this.bounds;
        }

        @Override
        public ClickableWidget getWidget() {
            return this;
        }
    }
}
