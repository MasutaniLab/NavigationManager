package RTC;


/**
* RTC/PathPlannerOperations.java .
* IDL-to-Java�R���p�C��(�|�[�^�u��)�A�o�[�W����"3.2"�ɂ���Đ�������܂���
* idl/MobileRobot.idl����
* 2014�N12��15�� 15��01��43�b JST
*/

public interface PathPlannerOperations 
{

  /// Plan Path from PathPlanParater.
  RTC.RETURN_VALUE planPath (RTC.PathPlanParameter param, RTC.Path2DHolder outPath);
} // interface PathPlannerOperations
