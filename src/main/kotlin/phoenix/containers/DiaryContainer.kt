package phoenix.containers

import mcp.MethodsReturnNonnullByDefault
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.container.Container
import net.minecraft.inventory.container.ContainerType
import net.minecraft.inventory.container.INamedContainerProvider
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent
import phoenix.client.gui.diaryPages.elements.ADiaryElement
import phoenix.init.PhoenixContainers
import java.util.*
import javax.annotation.ParametersAreNonnullByDefault


@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
class DiaryContainer(id: Int) :
    Container(PhoenixContainers.GUIDE, id), INamedContainerProvider
{
    var name: ITextComponent = StringTextComponent("Zahara")
    var allOpened: ArrayList<ADiaryElement>? = null
    var page = 0

    fun setName(nameIn: ITextComponent): DiaryContainer
    {
        name = nameIn
        return this
    }

    override fun canInteractWith(playerIn: PlayerEntity): Boolean
    {
        return true
    }

    override fun getDisplayName(): ITextComponent
    {
        return StringTextComponent(name.string + "'s Diary")
    }

    override fun createMenu(id: Int, inventory: PlayerInventory, entity: PlayerEntity): Container?
    {
        return this
    }

    companion object
    {
        fun fromNetwork(): ContainerType<DiaryContainer>
        {
            return ContainerType { id: Int, player: PlayerInventory? ->
                DiaryContainer(
                    id
                )
            }
        }
    }
}
