<?php

namespace App\Controller;

use App\Entity\Publication;
use App\Form\PubliactionFormType;
use App\Repository\PublicationRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;






class PublicationController extends AbstractController
{
    /**
     * @Route("/publication", name="publication")
     */
    public function index(): Response
    {
        return $this->render('publication/index.html.twig', [
            'controller_name' => 'PublicationController',
        ]);
    }



    /**
     * @param PublicationRepository $repo
     * @return \Symfony\Component\HttpFoundation\Response
     * @route ("/AfficherPublication",name="AffichePubliaction")
     */
    public function AfficherPublication (PublicationRepository  $repo){
        #$repo=$this->getDoctrine()->getRepository(publication::class);
        $publication=$repo->findAll();
        return $this->render('publication/affichePublication.html.twig',['publication'=>$publication]);
    }




    /**
     * @Route ("/SupprimerPublication/{id}",name="d")
     */
    function SupprimerPublication ($id,PublicationRepository $repo){
        $publication=$repo->findOneById($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($publication);
        $em->flush();
        return $this->redirectToRoute('affichePublication');
    }



    /**
     * @param Request $req
     * @return \Symfony\Component\HttpFoundation\Response
     * @Route ("/AjouterPublication", name="reclamation/Add")
     */
    function AjouterPublication (Request $req){
        $publication =new Publication();
        $form=$this->Createform(PubliactionFormType::class,$publication);
        $form->add('Ajouter',SubmitType::class);
        $form->handleRequest($req);
        if($form->isSubmitted() && $form->isValid()){
            $em=$this->getDoctrine()->getManager();
            $em->persist($publication);
            $em->flush();
            return $this->redirectToRoute('affichePublication');
        }
        return $this->render('publication/affichePublication.html.twig',['form'=>$form->createView()]);
    }



    /**
     * @Route ("/ModifierPublication/{id}",name="update")
     */
    function ModifierPublication (PublicationRepository  $repo,$id,Request $req){
        $publication=$repo->find($id);
        $form=$this->Createform(PubliactionFormType::class,$publication);
        $form->add('Update',SubmitType::class);
        $form->handleRequest($req);
        if($form->isSubmitted() && $form->isValid()){
            $em=$this->getDoctrine()->getManager();
            $em->persist($publication);
            $em->flush();
            return $this->redirectToRoute('affichePublication');
        }
        return $this->render('publication/modifier.html.twig',['form'=>$form->createView()]);
    }
}
