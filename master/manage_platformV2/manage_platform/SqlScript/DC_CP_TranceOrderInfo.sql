CREATE TABLE `DC_CP_TranceOrderInfo` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '��ID',
  `sycDate` date DEFAULT NULL COMMENT 'ͬ������',
  `userId` bigint(10) DEFAULT NULL COMMENT '��Ʊϵͳ�û�ID',
  `lotteryName` varchar(30) DEFAULT NULL COMMENT '��������',
  `playType` int(3) DEFAULT NULL COMMENT '�淨',
  `issue` varchar(10) DEFAULT NULL COMMENT '�����ڴ�',
  `schemeId` bigint(10) DEFAULT NULL COMMENT '����id',
  `schemeExtendId` bigint(10) DEFAULT NULL COMMENT '������չid',
  `money` double DEFAULT NULL COMMENT '���ʽ��',
  `buyTime` datetime DEFAULT NULL COMMENT '����ʱ��',
  `buyWay` int(1) DEFAULT '1' COMMENT '����ʽ( 1:ί�г�Ʊ��2:�Լ�ȡƱ)',
  `online` int(1) DEFAULT '1' COMMENT '��������(1:����   0:����)',
  `status` int(3) DEFAULT NULL COMMENT '����״̬ 1��֧���ɹ�   2:֧��ʧ��  3:��Ʊ�ɹ� 4:��Ʊʧ��',
  `orderNo` varchar(30) DEFAULT NULL COMMENT '�̻�������',
  `transNo` varchar(30) DEFAULT NULL COMMENT '������ˮ��',
  `isValid` int(1) DEFAULT '1' COMMENT '�Ƿ���Ч(1:��Ч  0:��Ч)',
  `ticketTime` int(11) DEFAULT NULL COMMENT '��Ʊʱ�� ',
  `createTime` datetime DEFAULT NULL COMMENT '����ʱ��',
  `schemeExtendType` int(5) DEFAULT NULL COMMENT '22:����׷����չ���� 23:����׷����չ����',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60147 DEFAULT CHARSET=utf8;

