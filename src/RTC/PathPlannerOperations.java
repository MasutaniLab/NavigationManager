package RTC;


/**
* RTC/PathPlannerOperations.java .
* IDL-to-Java�R���p�C��(�|�[�^�u��)�A�o�[�W����"3.2"�ɂ���Đ�������܂���
* idl/MobileRobot.idl����
* 2014�N9��4�� 22��56��50�b JST
*/

public interface PathPlannerOperations 
{

  /// Plan Path from PathPlanParater.
  RTC.RETURN_VALUE planPath (RTC.PathPlanParameter param, RTC.Path2DHolder path);
} // interface PathPlannerOperations
