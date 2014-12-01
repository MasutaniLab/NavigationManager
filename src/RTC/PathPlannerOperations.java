package RTC;


/**
* RTC/PathPlannerOperations.java .
* IDL-to-Java�R���p�C��(�|�[�^�u��)�A�o�[�W����"3.2"�ɂ���Đ�������܂���
* idl/MobileRobot.idl����
* 2014�N12��1�� 16��59��53�b JST
*/

public interface PathPlannerOperations 
{

  /// Plan Path from PathPlanParater.
  RTC.RETURN_VALUE planPath (RTC.OGMap map, RTC.TimedPose2D currentPose, RTC.TimedPose2D targetGoal, RTC.Path2DHolder outPath);
} // interface PathPlannerOperations
