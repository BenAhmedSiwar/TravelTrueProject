<?php

namespace App\Controller;

use App\Entity\Avis;
use App\Entity\Chambre;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class AvisController extends AbstractController
{
    /**
     * @Route("/ajouter_like/{id}", name="like")
     */
    public function like(Chambre $chambre , Avis $avis = null ): Response
    {
        if($avis == null)
        {
            $avis = new Avis();
        }
        $em = $this->getDoctrine()->getManager();
        $avis->setChambre($chambre);
        $avis->setCreatedat(new \DateTime('now'));
        $avis->setLikes($avis->getLikes()+1);
        if ($avis->getDislikes() == 0)
        {
            $avis->setDislikes(0);
        }
        else
        {
            $avis->setDislikes($avis->getDislikes()-1);
        }
        $em->persist($avis);
        $em->flush();
        return $this->redirectToRoute('chambre');

    }

    /**
     * @Route("/ajouter_dislike/{id}", name="dislike")
     */
    public function dislike(Chambre $chambre ,  Avis $avis = null): Response
    {
        if($avis == null)
        {
            $avis = new Avis();
        }
        $em = $this->getDoctrine()->getManager();
        $avis->setChambre($chambre);
        $avis->setCreatedat(new \DateTime('now'));
        $avis->setDislikes($avis->getDislikes()+1);
        if ($avis->getLikes() == 0)
        {
            $avis->setLikes(0);
        }
        else
        {
            $avis->setLikes($avis->getLikes()-1);
        }
        $em->persist($avis);
        $em->flush();
        return $this->redirectToRoute('chambre');
    }
}
