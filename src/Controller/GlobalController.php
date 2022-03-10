<?php

namespace App\Controller;

use App\Entity\Utilisateur;
use App\Form\InscriptionType;
use App\Repository\ChambreRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Security\Http\Authentication\AuthenticationUtils;

class GlobalController extends AbstractController
{
    /**
     * @Route("/", name="global")
     */
    public function index(): Response
    {
        return $this->render('global/index.html.twig');
    }

    /**
     * @Route("/admin", name="global_admin")
     */
    public function index_admin(): Response
    {
        return $this->render('global/admin.html.twig');
    }

    /**
     * @Route("/inscription", name="inscription")
     */
    public function Inscription(Request $request, UserPasswordEncoderInterface $encoder): Response
    {
        $utilisateur = new Utilisateur();
        $form = $this->createForm(InscriptionType::class, $utilisateur);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $passwordcrypt = $encoder->encodePassword($utilisateur, $utilisateur->getPassword());
            $utilisateur->setPassword($passwordcrypt);
            $utilisateur->setRoles('[ROLE_USER]');
            $em = $this->getDoctrine()->getManager();
            $em->persist($utilisateur);
            $em->flush();
            return $this->redirectToRoute('global');
        }
        return $this->render('global/Inscription.html.twig', [
            'form' => $form->createView()
        ]);
    }

    /**
     * @Route("/login", name="login")
     *
     */

    public function login(AuthenticationUtils $utils): Response
    {
        return $this->render('global/login.html.twig',[
            'LastUserName' => $utils->getLastUsername() ,
            'error' => $utils->getLastAuthenticationError()
        ]);
    }

    /**
     * @Route("/logout", name="logout")
     *
     */

    public function logout()
    {

    }
    /**
     * @Route("/stats",name="stats")
     */
    public function statistiques(ChambreRepository  $chambreRepository){
        //on va chercher toutes les chambres
        $chambre =$chambreRepository->findAll();

        $chambredes=[];
        $chambrecount=[];
        foreach ($chambre as $chambre){
            $chambredes[]= $chambre->getType();
            $chambrecount[]= count($chambre->getReservations());
        }
        return $this->render('global/stats.html.twig',[
            'chambredes'=>json_encode($chambredes),
            'chambrecount'=>json_encode($chambrecount)
        ]);
    }
}
