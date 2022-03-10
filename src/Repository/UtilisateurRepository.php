<?php

namespace App\Repository;

use App\Entity\Utilisateur;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\ORM\OptimisticLockException;
use Doctrine\ORM\ORMException;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method Utilisateur|null find($id, $lockMode = null, $lockVersion = null)
 * @method Utilisateur|null findOneBy(array $criteria, array $orderBy = null)
 * @method Utilisateur[]    findAll()
 * @method Utilisateur[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class UtilisateurRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Utilisateur::class);
    }

    /**
     * @throws ORMException
     * @throws OptimisticLockException
     */
    public function add(Utilisateur $entity, bool $flush = true): void
    {
        $this->_em->persist($entity);
        if ($flush) {
            $this->_em->flush();
        }
    }

    /**
     * @throws ORMException
     * @throws OptimisticLockException
     */
    public function remove(Utilisateur $entity, bool $flush = true): void
    {
        $this->_em->remove($entity);
        if ($flush) {
            $this->_em->flush();
        }
    }


    public function nombreadmin()
    {
        $qb = $this->createQueryBuilder('t');
        return $qb
            ->select('count(t.roles)')
            ->where('t.roles = :val')
            ->setParameter('val',"ROLE_ADMIN")
            ->getQuery()
            ->getSingleScalarResult();
    }

    public function nombreagent()
    {
        $qb = $this->createQueryBuilder('t');
        return $qb
            ->select('count(t.roles)')
            ->where('t.roles = :val')
            ->setParameter('val',"ROLE_AGENT")
            ->getQuery()
            ->getSingleScalarResult();
    }

    public function nombreclient()
    {
        $qb = $this->createQueryBuilder('t');
        return $qb
            ->select('count(t.roles)')
            ->where('t.roles = :val')
            ->setParameter('val',"ROLE_CLIENT")
            ->getQuery()
            ->getSingleScalarResult();
    }

    public function nombreemployee()
    {
        $qb = $this->createQueryBuilder('t');
        return $qb
            ->select('count(t.roles)')
            ->where('t.roles = :val')
            ->setParameter('val',"ROLE_EMPLOYE")
            ->getQuery()
            ->getSingleScalarResult();
    }

    public function lesagents()
    {
        return $this->createQueryBuilder('t')
            ->where('t.roles = :val')
            ->setParameter('val',"ROLE_AGENT")
            ->getQuery()
            ->getResult();
    }

    public function lesclients()
    {
        return $this->createQueryBuilder('t')
            ->where('t.roles = :val')
            ->setParameter('val',"ROLE_CLIENT")
            ->getQuery()
            ->getResult();
    }

    public function lesemp()
    {
        return $this->createQueryBuilder('t')
            ->where('t.roles = :val')
            ->setParameter('val',"ROLE_EMPLOYE")
            ->getQuery()
            ->getResult();
    }


    /*
    public function findOneBySomeField($value): ?Utilisateur
    {
        return $this->createQueryBuilder('u')
            ->andWhere('u.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */
}