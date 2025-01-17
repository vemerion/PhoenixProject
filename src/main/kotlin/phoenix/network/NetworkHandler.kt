package phoenix.network

import net.minecraft.client.entity.player.ClientPlayerEntity
import net.minecraft.entity.Entity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.network.NetworkManager
import net.minecraft.network.PacketBuffer
import net.minecraft.util.ResourceLocation
import net.minecraft.world.chunk.Chunk
import net.minecraft.world.dimension.DimensionType
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fml.LogicalSide
import net.minecraftforge.fml.network.NetworkEvent
import net.minecraftforge.fml.network.NetworkRegistry
import net.minecraftforge.fml.network.PacketDistributor
import net.minecraftforge.fml.network.PacketDistributor.TargetPoint
import phoenix.Phoenix
import phoenix.utils.clientPlayer
import java.util.function.Supplier


object NetworkHandler
{
    private val CHANNEL = NetworkRegistry.newSimpleChannel(ResourceLocation(Phoenix.MOD_ID, "network"), { "2.0" }, { true }, { true })

    fun init()
    {
        registerPacket(SyncStagePacket().javaClass)
        registerPacket(SyncBookPacket(ArrayList()))
        registerPacket(SyncOvenPacket().javaClass)
        registerPacket(SyncTankPacket().javaClass)
        registerPacket(OpenCaudaInventoryPacket(0).javaClass)
    }

    private var id: Short = 0

    fun sendTo(packet: Packet, player: ServerPlayerEntity) = CHANNEL.send(PacketDistributor.PLAYER.with { player }, packet)

    fun sendToAll(packet: Packet) = CHANNEL.send(PacketDistributor.ALL.noArg(), packet)

    fun sendToNear(packet: Packet, point: TargetPoint) = CHANNEL.send(PacketDistributor.NEAR.with { point }, packet)

    fun sendToServer(packet: Packet) = CHANNEL.send(PacketDistributor.SERVER.noArg(), packet)

    fun sendToDim(packet: Packet, type: DimensionType) = CHANNEL.send(PacketDistributor.DIMENSION.with { type }, packet)

    /**
     * Данный метод позволяет отправить наш пакет всем отслеживающим сущность. Пакет отсылается на КЛИЕНТ!
     *
     * @param packet - наш пакет
     * @param entity - сущность, которую нужно отправить отслеживающим
     */
    fun sendToTracking(packet: Packet, entity: Entity)
    {
        CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with { entity }, packet)
    }

    /**
     * Данный метод позволяет отправить наш пакет всем отслеживающим сущность и игрока. Пакет отсылается на КЛИЕНТ!
     *
     * @param packet - наш пакет
     * @param entity - сущность, которую нужно отправить отслеживающим
     */
    fun sendToTrackingAndSelf(packet: Packet, entity: Entity) = CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with { entity }, packet)

    /**
     * Данный метод позволяет отправить наш пакет всем отслеживающим чанк. Пакет отсылается на КЛИЕНТ!
     *
     * @param packet - наш пакет
     * @param chunk  - чанк, который нужно отправить отслеживающим
     */
    fun sendToTrackingChunk(packet: Packet, chunk: Chunk) = CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with { chunk }, packet)

    /**
     * Данный метод позволяет отправить наш пакет всем `менеджерам`. Пакет отсылается на КЛИЕНТ!
     * Вы можете использовать данный метод для отправки пакетов ТОЛЬКО некоторым игрокам, а не всем или тем кто находится по близости.
     * Пример использования: Создайте список NetworkManager, а затем добавьте в него
     * `p.connection.netManager` (там где p это EntityPlayerMP!)
     *
     * @param packet   - наш пакет
     * @param managers - список менеджеров, которым нужно отправить пакет.
     */
    fun sendToSeveralPlayers(packet: Packet, managers: List<NetworkManager>) = CHANNEL.send(PacketDistributor.NMLIST.with { managers }, packet)

    private fun registerPacket(clazz: Class<Packet>)
    {
        val packet = clazz.newInstance()
        CHANNEL.registerMessage(id++.toInt(), clazz, packet::encode, packet::decode, packet::handlePacket)
    }

    private fun registerPacket(packet: Packet)
    {
        CHANNEL.registerMessage(id++.toInt(), packet.javaClass, packet::encode, packet::decode, packet::handlePacket)
    }

    abstract class Packet
    {
        abstract fun encode(packet: Packet, buf: PacketBuffer)
        abstract fun decode(buf: PacketBuffer): Packet

        fun handlePacket(packet: Packet, context: Supplier<NetworkEvent.Context>)
        {
            val ctx: NetworkEvent.Context = context.get()

            when (ctx.direction.receptionSide)
            {
                LogicalSide.CLIENT -> this.client(clientPlayer)
                LogicalSide.SERVER -> this.server(ctx.sender)
                null               -> this.server(ctx.sender)
            }
            ctx.packetHandled = true
        }

        @OnlyIn(Dist.CLIENT)
        abstract fun client(player: ClientPlayerEntity?)
        @OnlyIn(Dist.DEDICATED_SERVER)
        abstract fun server(player: ServerPlayerEntity?)

        fun sendToAll() = sendToAll(this)
        fun sendToServer() = sendToServer(this)
        fun sandTo(target : PacketDistributor.PacketTarget) = CHANNEL.send(target, this)
        fun sendTo(type : DimensionType) = sendToDim(this, type)
    }
}