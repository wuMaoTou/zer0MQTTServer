package com.syxy.protocol.mqttImp;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import com.syxy.protocol.mqttImp.message.ConnectMessage;
import com.syxy.protocol.mqttImp.message.Message;
import com.syxy.protocol.mqttImp.message.PackageIdVariableHeader;
import com.syxy.protocol.mqttImp.message.PublishMessage;
import com.syxy.protocol.mqttImp.message.SubscribeMessage;
import com.syxy.protocol.mqttImp.message.UnSubscribeMessage;
import com.syxy.protocol.mqttImp.process.ProtocolProcess;

/**
 *  MQTT协议业务处理
 * 
 * @author zer0
 * @version 1.0
 * @date 2015-2-16
 */
public class MQTTProcess extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ProtocolProcess process = ProtocolProcess.getInstance();
		Message message = (Message)msg;
		
		switch (message.getFixedHeader().getMessageType()) {
		case CONNECT:
			process.processConnect(ctx.channel(), (ConnectMessage)message);
			break;
		case CONNACK:
			break;
		case PUBLISH:
			process.processPublic(ctx.channel(), (PublishMessage)message);
			break;
		case PUBACK:
			process.processPubAck(ctx.channel(), (PackageIdVariableHeader)message.getVariableHeader());
			break;
		case PUBREL:
			process.processPubRel(ctx.channel(), (PackageIdVariableHeader)message.getVariableHeader());
			break;
		case PUBREC:
			process.processPubRec(ctx.channel(), (PackageIdVariableHeader)message.getVariableHeader());
			break;
		case PUBCOMP:
			process.processPubComp(ctx.channel(), (PackageIdVariableHeader)message.getVariableHeader());
			break;
		case SUBSCRIBE:
			process.processSubscribe(ctx.channel(), (SubscribeMessage)message);
			break;
		case SUBACK:
			break;
		case UNSUBSCRIBE:
			process.processUnSubscribe(ctx.channel(), (UnSubscribeMessage)message);
			break;
		case UNSUBACK:
			break;
		case PINGREQ:
			process.processPingReq(ctx.channel(), message);
			break;
		case DISCONNECT:
			process.processDisconnet(ctx.channel(), message);
			break;

		default:
			break;
		}
	}

}
