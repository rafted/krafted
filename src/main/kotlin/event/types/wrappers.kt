package event.types

import protocol.packet.impl.login.clientbound.LoginSuccessEvent
import protocol.packet.impl.login.serverbound.LoginStartEvent
import protocol.packet.impl.status.clientbound.Response

typealias PlayerLoginEvent = LoginSuccessEvent
typealias PrePlayerLoginEvent = LoginStartEvent
typealias ResponsePacketEvent = Response
typealias ServerListPingEvent = ResponsePacketEvent