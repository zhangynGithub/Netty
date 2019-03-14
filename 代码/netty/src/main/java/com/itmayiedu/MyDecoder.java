package com.itmayiedu;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * @author Chalmers 2016年2月24日 下午2:23:49
 */
public class MyDecoder extends FrameDecoder {

	@Override
	protected Object decode(ChannelHandlerContext chc, Channel channel, ChannelBuffer buffer) throws Exception {

		// 如果buffer中的可读字节大于4个（即除了长度以外还有数据，因为长度可能是为0的）
		if (buffer.readableBytes() > 4) {

			// 标记，指向当前指针位置，读取数据时使用
			buffer.markReaderIndex();
			// 取得长度
			int len = buffer.readInt();

			// 如果剩余可读字节小于长度的话，则表明发生了拆包现象，那么不对它进行处理
			if (buffer.readableBytes() < len) {
				// 重置标记
				buffer.resetReaderIndex();

				// 返回null，表示等待
				return null;
			}

			// 对数据进行处理
			byte[] bytes = new byte[len];
			buffer.readBytes(bytes);
			// 将数据返回到ServerHandler中进行处理
			return new String(bytes);
		}

		return null;
	}

}