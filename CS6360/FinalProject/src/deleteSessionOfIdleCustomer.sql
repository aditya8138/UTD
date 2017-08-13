CREATE OR REPLACE PROCEDURE deleteSessionOfIdleCustomer
  ( WithoutMemerCard      IN NUMBER DEFAULT 730,
    WithExpireMemerCard   IN NUMBER DEFAULT 1095 ) AS
  CURSOR CustomerWithoutMemberCard IS
    SELECT  CUSTOMER.PHONE
    FROM    CUSTOMER
    WHERE   PHONE NOT IN (SELECT PHONE FROM MEMBERCARD);
  CURSOR CustomerWithExpireMemberCard IS
    SELECT  CUSTOMER.PHONE
    FROM    CUSTOMER
    WHERE   PHONE IN (
      SELECT PHONE FROM MEMBERCARD WHERE EXPIRATIONDATE < SYSDATE);
  thisWithoutCard CustomerWithoutMemberCard%ROWTYPE;
  thisExpiredCard CustomerWithExpireMemberCard%ROWTYPE;
BEGIN
  OPEN CustomerWithoutMemberCard;
  LOOP
    FETCH CustomerWithoutMemberCard INTO thisWithoutCard;
    EXIT WHEN CustomerWithoutMemberCard%NOTFOUND;
    DELETE FROM EXERCISESESSION
      WHERE CUSTOMERPHONE = thisWithoutCard.PHONE
        AND SDATE < SYSDATE - WithoutMemerCard;
   END LOOP;
  CLOSE CustomerWithoutMemberCard;
  OPEN CustomerWithExpireMemberCard;
  LOOP
    FETCH CustomerWithExpireMemberCard INTO thisExpiredCard;
    EXIT WHEN CustomerWithExpireMemberCard%NOTFOUND;
    DELETE FROM EXERCISESESSION
      WHERE CUSTOMERPHONE = thisExpiredCard.PHONE
        AND SDATE < SYSDATE - WithExpireMemerCard;
   END LOOP;
  CLOSE CustomerWithExpireMemberCard;
END;/
