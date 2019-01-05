package ladysnake.dissolution.mixin.client.render.gl;

import net.minecraft.client.gl.GlProgram;
import net.minecraft.client.gl.GlShader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Minecraft does not take into account domains when parsing a shader program.
 * These hooks redirect identifier instantiations to allow specifying a domain for shader files.
 */
@Mixin(GlProgram.class)
public class GlProgramMixin {
    /**
     * @param arg the string passed to the redirected Identifier constructor
     * @param id the actual id passed as an argument to the method
     * @return a new Identifier
     */
    @Redirect(
            at = @At(
                    value = "NEW",
                    target = "(Ljava/lang/String;)Lnet/minecraft/util/Identifier;"
            ),
            method = "<init>"
    )
    public Identifier constructProgramIdentifier(String arg, ResourceManager unused, String id) {
        if (!arg.contains(":")) {
            return new Identifier(arg);
        }
        Identifier split = new Identifier(id);
        return new Identifier(split.getNamespace(), "shaders/program/" + split.getPath() + ".json");
    }

    /**
     * @param arg the string passed to the redirected Identifier constructor
     * @param id the actual id passed as an argument to the method
     * @return a new Identifier
     */
    @Redirect(
            at = @At(
                    value = "NEW",
                    target = "(Ljava/lang/String;)Lnet/minecraft/util/Identifier;"
            ),
            method = "method_16036"
    )
    private static Identifier constructProgramIdentifier(String arg, ResourceManager unused, GlShader.Type shaderType, String id) {
        if (!arg.contains(":")) {
            return new Identifier(arg);
        }
        Identifier split = new Identifier(id);
        return new Identifier(split.getNamespace(), "shaders/program/" + split.getPath() + shaderType.getFileExtension());
    }
}
