package RTC;

/**
* RTC/PathPlanPrameterHolder.java .
* IDL-to-Java�R���p�C��(�|�[�^�u��)�A�o�[�W����"3.2"�ɂ���Đ�������܂���
* idl/MobileRobot.idl����
* 2014�N9��4�� 22��56��14�b JST
*/

public final class PathPlanPrameterHolder implements org.omg.CORBA.portable.Streamable
{
  public RTC.PathPlanPrameter value = null;

  public PathPlanPrameterHolder ()
  {
  }

  public PathPlanPrameterHolder (RTC.PathPlanPrameter initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = RTC.PathPlanPrameterHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    RTC.PathPlanPrameterHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return RTC.PathPlanPrameterHelper.type ();
  }

}
